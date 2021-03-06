package tinkersoc.furnace;

import li.cil.oc.api.API;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.prefab.AbstractManagedEnvironment;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.smeltery.tileentity.TileSmeltery;
import slimeknights.tconstruct.smeltery.tileentity.TileSearedFurnace;
import li.cil.oc.api.Network;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.machine.Arguments;
import tinkersoc.AbstractTinkersEnvironment;

import java.util.HashMap;

public class EnvironmentTinkerFurnace extends AbstractTinkersEnvironment<TileSearedFurnace> {
	

	public EnvironmentTinkerFurnace(TileSearedFurnace furnace)
	{
		super("seared_furnace", furnace);

	}

	@Callback(doc = "function():table - Get information on the furnace's current fuel")
	public Object[] getFuelInfo(final Context context, Arguments arguments)
	{
		FluidStack stack = tile.currentFuel;
		return new Object[]{new HashMap<String, Object>() {
			{
				put("fluid", stack);
				put("heat", stack.getFluid().getTemperature());
				put("maxCap", stack.amount);
			}

		}
		};
	}

	@Callback(doc = "function():int - Gets the amount of fuel in the furnace")
	public Object[] getFuelLevel(final Context context, Arguments arguments)
	{
		return new Object[] {tile.currentFuel.amount};
	}


	@Callback(doc = "function([index:int]):int - Gets the furnace temperature or the temperature of an item being smelted")
	public Object[] getTemperature(final Context context, Arguments arguments)
	{
		if (arguments.count() == 0) { return new Object[] { tile.getTemperature() }; }

		return new Object[] {tile.getTemperature(arguments.checkInteger(0))};
	}

	@Callback(doc = "function(index:int) - Returns whether the item in the specified slot can be heated")
	public Object[] canHeat(final Context context, Arguments arguments)
	{
		return new Object[] {tile.canHeat(arguments.checkInteger(0))};
	}

	@Callback(doc = "function():int - Gets the number of smelting slots the furnace has")
	public Object[] getInventorySize(final Context context, Arguments arguments)
	{
		return new Object[] {tile.getSizeInventory()};
	}

	@Callback(doc = "function(index:int):int - Gets the required temperature for the item in the specified slot")
	public Object[] getTempRequired(final Context context, Arguments arguments)
	{
		return new Object[] {tile.getTempRequired(arguments.checkInteger(0))};
	}

	@Callback(doc = "function():boolean - Gets whether the furnace has fuel")
	public Object[] hasFuel(final Context context, Arguments arguments)
	{
		return new Object[] {tile.hasFuel()};
	}

	@Callback(doc = "function(index:int):table - Gets the stack in the specified slot")
	public Object[] getStackInSlot(final Context context, Arguments arguments)
	{

		if (!API.config.getBoolean("misc.allowItemStackInspection")) { return new Object[] {null, "ItemStack inspection disabled in OC config"}; }

		int slot = arguments.checkInteger(0);
		if (slot < 1 || slot > tile.getSizeInventory()) { return new Object[] {null, "Invalid slot"}; }

		slot--;

		ItemStack stack = tile.getItemHandler().getStackInSlot(slot);

		if(stack.isEmpty()) { return new Object[] {null, "No item"}; }

		return new Object[] {stack};

	}

	@Callback(doc = "function(index:int):number - Gets the heating progress of the item in the specified slot")
	public Object[] getHeatingProgress(final Context context, Arguments arguments)
	{
		return new Object[] { tile.getHeatingProgress(arguments.checkInteger(0)) };
	}


}
