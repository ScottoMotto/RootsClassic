package elucent.rootsclassic.block;

import elucent.rootsclassic.Roots;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMidnightBloom extends BlockBush {

  private static final AxisAlignedBB AXIS_ALIGNED_BB = new AxisAlignedBB(0.375, 0, 0.375, 0.625, 0.5, 0.625);

  public BlockMidnightBloom() {
    super();
    setCreativeTab(Roots.tab);
  }

  @SideOnly(Side.CLIENT)
  public void initModel() {
    ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
  }

  @Override
  public boolean isOpaqueCube(IBlockState state) {
    return false;
  }

  @Override
  public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
    return (layer == BlockRenderLayer.CUTOUT);
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
    return AXIS_ALIGNED_BB;
  }

  @Override
  public boolean isFullCube(IBlockState state) {
    return false;
  }
}
