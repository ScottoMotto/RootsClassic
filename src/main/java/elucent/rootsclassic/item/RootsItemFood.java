package elucent.rootsclassic.item;

import java.util.List;
import javax.annotation.Nullable;
import elucent.rootsclassic.RegistryManager;
import elucent.rootsclassic.Roots;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RootsItemFood extends ItemFood {

  private static final int HEAL_LARGE = 5;
  private static final int HEAL_SMALL = 2;

  public RootsItemFood(int amount, float saturation, boolean isWolFFood) {
    super(amount, saturation, isWolFFood);
    this.setCreativeTab(Roots.tab);
  }

  @Override
  public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
    super.onItemUseFinish(stack, worldIn, entityLiving);
    if (stack.getItem() == RegistryManager.redCurrant) {
      entityLiving.heal(HEAL_SMALL);
    }
    if (stack.getItem() == RegistryManager.elderBerry) {
      entityLiving.clearActivePotions();
    }
    if (stack.getItem() == RegistryManager.healingPoultice) {
      entityLiving.heal(HEAL_LARGE);
    }
    return stack;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    if (stack.getItem() == RegistryManager.redCurrant) {
      tooltip.add(I18n.format("roots.healingitem.tooltip"));
    }
    if (stack.getItem() == RegistryManager.elderBerry) {
      tooltip.add(I18n.format("roots.clearpotionsitem.tooltip"));
    }
    if (stack.getItem() == RegistryManager.healingPoultice) {
      tooltip.add(I18n.format("roots.healingitem.tooltip", HEAL_LARGE));
    }
    if (stack.getItem() == RegistryManager.nightshade) {
      tooltip.add(I18n.format("roots.poisonitem.tooltip"));
    }
  }

  @SideOnly(Side.CLIENT)
  public void initModel() {
    ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
  }
}
