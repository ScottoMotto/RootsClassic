package elucent.rootsclassic.tileentity;

import java.util.ArrayList;
import elucent.rootsclassic.RegistryManager;
import elucent.rootsclassic.component.ComponentManager;
import elucent.rootsclassic.component.ComponentRecipe;
import elucent.rootsclassic.item.ItemDustPetal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class TileEntityMortar extends TEBase {

  public ArrayList<ItemStack> inventory = new ArrayList<ItemStack>();

  public TileEntityMortar() {
    super();
  }

  @Override
  public void readFromNBT(NBTTagCompound tag) {
    super.readFromNBT(tag);
    inventory = new ArrayList<ItemStack>();
    if (tag.hasKey("modifiers")) {
      NBTTagList list = tag.getTagList("modifiers", Constants.NBT.TAG_COMPOUND);
      for (int i = 0; i < list.tagCount(); i++) {
        inventory.add(new ItemStack(list.getCompoundTagAt(i)));
      }
    }
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound tag) {
    super.writeToNBT(tag);
    if (inventory.size() > 0) {
      NBTTagList list = new NBTTagList();
      for (int i = 0; i < inventory.size(); i++) {
        ;
        list.appendTag(inventory.get(i).writeToNBT(new NBTTagCompound()));
      }
      tag.setTag("modifiers", list);
    }
    return tag;
  }

  @Override
  public void breakBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
    for (int i = 0; i < inventory.size(); i++) {
      if (!world.isRemote) {
        world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, inventory.get(i)));
      }
    }
    this.invalidate();
  }

  @Override
  public boolean activate(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
    if (heldItem == null) {
      if (inventory.size() > 0) {
        if (!world.isRemote) {
          world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, inventory.remove(inventory.size() - 1)));
        }
        else {
          inventory.remove(inventory.size() - 1);
        }
        markDirty();
        this.getWorld().notifyBlockUpdate(getPos(), state, world.getBlockState(pos), 3);
        return true;
      }
    }
    else if (heldItem.getItem() == RegistryManager.pestle) {
      ComponentRecipe recipe = ComponentManager.getRecipe(inventory);
      if (recipe != null) {
        if (!recipe.isDisabled() && this.inventory.size() > 3) {
          if (recipe != null && ComponentRecipe.getModifierCapacity(inventory) != -1) {
            ItemStack drop = new ItemStack(RegistryManager.dustPetal, 1);
            ItemDustPetal.createData(drop, player, recipe.getEffectResult(), inventory);
            if (!world.isRemote) {
              world.spawnEntity(new EntityItem(world, getPos().getX() + 0.5, getPos().getY() + 0.5, getPos().getZ() + 0.5, drop));
            }
            inventory.clear();
            markDirty();
            this.getWorld().notifyBlockUpdate(getPos(), state, world.getBlockState(pos), 3);

            return true;
          }
        }
      }
    }
    else {
      if (inventory.size() < 8) {
        if (heldItem.getItem() == Items.GLOWSTONE_DUST || heldItem.getItem() == Items.REDSTONE || heldItem.getItem() == Items.GUNPOWDER) {
          int maxCapacity = ComponentRecipe.getModifierCapacity(inventory);
          int modifierCount = ComponentRecipe.getModifierCount(inventory);
          if (modifierCount < maxCapacity) {
            inventory.add(new ItemStack(heldItem.getItem(), 1, heldItem.getMetadata()));
            heldItem.shrink(1);
            markDirty();
            this.getWorld().notifyBlockUpdate(getPos(), state, world.getBlockState(pos), 3);
            return true;
          }
        }
        else {
          ItemStack oneItem = new ItemStack(heldItem.getItem(), 1, heldItem.getItemDamage());
          if (heldItem.hasTagCompound()) {
            oneItem.setTagCompound(heldItem.getTagCompound());
          }
          inventory.add(oneItem);
          heldItem.shrink(1);
          markDirty();
          this.getWorld().notifyBlockUpdate(getPos(), state, world.getBlockState(pos), 3);
          return true;
        }
      }
    }
    return false;
  }
}
