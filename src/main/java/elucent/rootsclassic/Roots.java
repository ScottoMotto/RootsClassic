package elucent.rootsclassic;

import elucent.rootsclassic.capability.RootsCapabilityManager;
import elucent.rootsclassic.config.EventConfigChanged;
import elucent.rootsclassic.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Const.MODID, useMetadata = true, updateJSON = "https://raw.githubusercontent.com/PrinceOfAmber/RootsClassic/master/update.json", acceptableRemoteVersions = "*", acceptedMinecraftVersions = "[1.12,)", guiFactory = "elucent." + Const.MODID + ".config.IngameConfigFactory")
public class Roots {

  public static CreativeTabs tab = new CreativeTabs(Const.MODID) {

    @Override
    public String getTabLabel() {
      return Const.MODID;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getTabIconItem() {
      return new ItemStack(RegistryManager.dustPetal);
    }
  };
  @SidedProxy(clientSide = "elucent." + Const.MODID + ".proxy.ClientProxy", serverSide = "elucent." + Const.MODID + ".proxy.ServerProxy")
  public static CommonProxy proxy;
  @Instance(Const.MODID)
  public static Roots instance;

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    ConfigManager.load(event);
    MinecraftForge.EVENT_BUS.register(new EventManager());
    MinecraftForge.EVENT_BUS.register(new RootsCapabilityManager());
    MinecraftForge.EVENT_BUS.register(new EventConfigChanged());
    MinecraftForge.EVENT_BUS.register(RegistryManager.class);
    proxy.preInit(event);
  }

  @EventHandler
  public void init(FMLInitializationEvent event) {
    proxy.init(event);
  }

  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {
    proxy.postInit(event);
  }
}
