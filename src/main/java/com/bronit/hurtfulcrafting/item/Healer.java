package com.bronit.hurtfulcrafting.item;

import com.bronit.hurtfulcrafting.HurtfulCrafting;
import com.bronit.hurtfulcrafting.handler.ItemHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class Healer extends Item {

    public static final String name = "healer";

    public Healer() {
        super();
        this.setCreativeTab(CreativeTabs.TOOLS);
        this.setMaxStackSize(16);

        this.setTranslationKey(name);

        ItemHandler.registerItem(this, HurtfulCrafting.getIdentifier(name));
        registerModel();
    }

    @Override
    public boolean hasEffect(@Nonnull final ItemStack stack) {
        return false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (handIn.equals(EnumHand.MAIN_HAND) && playerIn.getMaxHealth() < 20) {
            playerIn.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(playerIn.getMaxHealth() + HurtfulCrafting.config.heal);
            playerIn.getHeldItem(EnumHand.MAIN_HAND).setCount(playerIn.getHeldItem(EnumHand.MAIN_HAND).getCount() - 1);
        }

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @SideOnly(Side.CLIENT)
    public void registerModel() {
        ModelLoader.setCustomModelResourceLocation(
                this,
                0,
                new ModelResourceLocation(HurtfulCrafting.Constants.MOD_ID + ":" + name, null)
        );
    }

}
