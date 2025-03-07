package com.foodcraft.proxy;

import com.foodcraft.FoodCraft;
import com.foodcraft.GuiIDs;
import com.foodcraft.gui.containers.*;
import com.foodcraft.gui.tileentities.*;
import com.foodcraft.init.FoodcraftBlocks;
import com.foodcraft.init.FoodcraftGuiBlocks;
import com.foodcraft.init.FoodcraftItems;
import com.foodcraft.init.FoodcraftPlants;
import com.foodcraft.init.FoodcraftRecipe;
import com.foodcraft.network.PacketDispatcher;
import com.foodcraft.network.Proxy;
import com.foodcraft.network.handler.HeatHandler;
import com.foodcraft.network.handler.HeatHandlerPot;
import com.foodcraft.network.message.HeatMessage;
import com.foodcraft.network.message.HeatMessagePot;
import com.foodcraft.util.GUIHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;


public class CommonProxy implements Proxy {

	public void preInit(FMLPreInitializationEvent event) {}
	
	public void init(FMLInitializationEvent event) {
		FoodcraftBlocks.init();
		FoodcraftGuiBlocks.init();
		FoodcraftPlants.init();
		FoodcraftItems.init();
		FoodcraftRecipe.init();
		regesterTileEntity();
		registerPacket();
		NetworkRegistry.INSTANCE.registerGuiHandler(FoodCraft.instance, new GUIHandler());
	}
	public void postInit(FMLPostInitializationEvent event) {}
	
	private void regesterTileEntity(){
		 GameRegistry.registerTileEntity(TileEntityMill.class, "TileEntityNmj");
		 GameRegistry.registerTileEntity(TileEntityPan.class, "TileEntityPDG");
		 GameRegistry.registerTileEntity(TileEntityPot.class, "TileEntityGuo");
		 GameRegistry.registerTileEntity(TileEntityPressureCooker.class, "TileEntityGyg");
		 GameRegistry.registerTileEntity(TileEntityChoppingBoard.class, "TileEntityCaiban");
		 GameRegistry.registerTileEntity(TileEntityFrying.class, "TileEntityYZJ");
		 GameRegistry.registerTileEntity(TileEntityBrewBarrel.class, "TileEntityNt");
		 GameRegistry.registerTileEntity(TileEntityStove.class, "TileEntityZl");
		 GameRegistry.registerTileEntity(TileEntityBeverageMaking.class, "TileEntityTpj");
	}

	private void registerPacket() {
		PacketDispatcher.instance.registerMessage(HeatHandler.class, HeatMessage.class);
		PacketDispatcher.instance.registerMessage(HeatHandlerPot.class, HeatMessagePot.class);
	}

	@Override
	public EntityPlayer getPlayer(MessageContext ctx) {
		return ctx.getServerHandler().playerEntity;
	}
}
