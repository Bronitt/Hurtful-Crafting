package com.bronit.hurtfulcrafting.item;

import com.bronit.hurtfulcrafting.HurtfulCrafting;
import com.bronit.hurtfulcrafting.Tags;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import static com.bronit.hurtfulcrafting.event.CraftingEvent.db;

public class Healer extends Item {

    public static final String name = "healer";

    public Healer() {
        super();
        this.setCreativeTab(CreativeTabs.TOOLS);
        this.setMaxStackSize(16);

        this.setTranslationKey(name);

        setRegistryName(HurtfulCrafting.getIdentifier(name));
        registerModel();
    }

    @Override
    public boolean hasEffect(@NotNull final ItemStack stack) {
        return false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

        NBTTagCompound nbt = new NBTTagCompound();
        db.writeToNBT(nbt);
        NBTTagCompound players = (NBTTagCompound) nbt.getTag("players");
        String uuid = EntityPlayer.getUUID(playerIn.getGameProfile()).toString();
        if (players.getKeySet().contains(uuid)) {
            players.removeTag(uuid);
            nbt.setTag("players", players);
            NBTTagCompound playerPos = (NBTTagCompound) players.getTag(uuid);
            int x = playerPos.getInteger("x");
            int y = playerPos.getInteger("y");
            int z = playerPos.getInteger("z");
            playerIn.posX = x;
            playerIn.posY = y;
            playerIn.posZ = z;
            playerIn.setPositionAndUpdate(x, y, z);
            db.readFromNBT(nbt);
            db.markDirty();
            playerIn.setGameType(GameType.SURVIVAL);
            playerIn.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
        } else {
            if (handIn.equals(EnumHand.MAIN_HAND)) {
                if (playerIn.getMaxHealth() < 20) {
                    playerIn.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(playerIn.getMaxHealth() + HurtfulCrafting.config.heal);
                    if (!playerIn.capabilities.isCreativeMode)
                        playerIn.getHeldItem(EnumHand.MAIN_HAND).setCount(playerIn.getHeldItem(EnumHand.MAIN_HAND).getCount() - 1);
                }
                playerIn.heal(1.0f);
            }
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @SideOnly(Side.CLIENT)
    public void registerModel() {
        ModelLoader.setCustomModelResourceLocation(
                this,
                0,
                new ModelResourceLocation(Tags.MOD_ID + ":" + name, null)
        );
    }

}
