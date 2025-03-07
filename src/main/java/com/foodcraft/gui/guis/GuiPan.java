package com.foodcraft.gui.guis;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import com.foodcraft.gui.containers.ContainerPan;
import com.foodcraft.gui.tileentities.TileEntityPan;
import com.foodcraft.network.PacketDispatcher;
import com.foodcraft.network.message.HeatMessage;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiPan extends GuiContainer{

	private TileEntityPan tile;

	public GuiPan(InventoryPlayer playerInv, TileEntityPan furnaceInv) {
		super(new ContainerPan(playerInv, furnaceInv));
		this.tile = furnaceInv;
		this.doesGuiPauseGame();
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(new ResourceLocation("foodcraft", "textures/gui/container/pdg.png"));
        int k = (this.width - this.xSize) / 2;//x
        int l = (this.height - this.ySize) / 2;//y
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        float i1;
        if(tile.furnaceCookTime > 0) {
        	if(tile.min > 0 && tile.max > 0) {
        		this.drawTexturedModalRect(k + 46, l + 62, 176, 31, (int)((float)tile.Nowheat * 77F /1000F), 3);
        		this.drawTexturedModalRect(k + 45 + (int)((float)(((float)tile.min)/ 1000F) * 77), l + 61, 176, 34, 1, 5);
        		this.drawTexturedModalRect(k + 45 + (int)((float)(((float)tile.max)/ 1000F) * 77), l + 61, 177, 34, 1, 5);
        	}
        }
        if (this.tile.isfire()) {
            i1 = this.tile.getBurnTimeRemainingScaled(14);
            this.drawTexturedModalRect(k + 81,(int)(l + 33 - i1), 176,(int)(15 - i1), 14, (int)(i1));
        }
        i1 = this.tile.getCookProgressScaled(24);
        this.drawTexturedModalRect(k + 72, l + 39, 176, 14, (int)(i1), 16);

        if(mouseX >= k + 81 && mouseX <= k + 81 + 14 && mouseY >= l + 19 && mouseY <= l + 19 + 14) {
            String s = null;
        	if(this.tile.currentItemBurnTime <= 20) {
        		s = StatCollector.translateToLocal("gui.lowfire.name");
        	}
        	else{if(this.tile.currentItemBurnTime <= 40 && this.tile.currentItemBurnTime > 20) {
            		s = StatCollector.translateToLocal("gui.mlowfire.name");
            	}
        		else{if(this.tile.currentItemBurnTime <= 60 && this.tile.currentItemBurnTime > 40) {
                		s = StatCollector.translateToLocal("gui.medfire.name");
                	}
        			else{if(this.tile.currentItemBurnTime <= 80 && this.tile.currentItemBurnTime > 60) {
                    		s = StatCollector.translateToLocal("gui.mhighfire.name");
                    	}
        				else{if(this.tile.currentItemBurnTime <= 100 && this.tile.currentItemBurnTime > 80) {
                        		s = StatCollector.translateToLocal("gui.highfire.name");
                        	}
        				}
        			}
        		}
        	}
        	this.drawCreativeTabHoveringText(this.tile.currentItemBurnTime + "%, " + s, mouseX, mouseY);
	        }
        
        this.fontRendererObj.drawString(StatCollector.translateToLocal("gui.UseF.name") + 
        		this.tile.frequencyOfUse, k + 7, l + 68, 4210752);
    }
    
	
	@Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = StatCollector.translateToLocal("TitlePDG");
		this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
	}
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int type) {
		int k = (this.width - this.xSize) / 2;//x
		int l = (this.height - this.ySize) / 2;//y
		try {
			super.mouseClicked(mouseX, mouseY, type);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(type == 0) {
			if (this.tile.isfire()) {
				if(mouseX >= k + 81 && mouseX <= k + 81 + 14 && mouseY >= l + 19 && mouseY < l + 32) {
					int i1 = mouseY - (l + 18);
					float f1 = ((float)i1)/14;
					NBTTagCompound nbt = new NBTTagCompound();
			    	  nbt.setInteger("x", tile.getPos().getX());
			    	  nbt.setInteger("y", tile.getPos().getY());
			    	  nbt.setInteger("z", tile.getPos().getZ());
			    	  nbt.setFloat("f1", f1);
			    	   PacketDispatcher.instance.sendToServer(new HeatMessage(nbt));
				}
			}
		}
	}
}
