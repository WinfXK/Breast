package cn.winfxk.breast;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.Utils;
import cn.winfxk.breast.cmd.MyCommand;
import cn.winfxk.breast.money.EconomyAPI;
import cn.winfxk.breast.money.EconomyManage;
import cn.winfxk.breast.money.MyEconomy;
import cn.winfxk.breast.tool.EnchantList;
import cn.winfxk.breast.tool.ItemList;
import cn.winfxk.breast.tool.Tool;
import cn.winfxk.breast.tool.Update;

/**
 * @author Winfxk
 */
public class Activate {
	protected Breast mis;
	public Player setPlayer;
	public ResCheck resCheck;
	public final static String[] FormIDs = { /* 0 */"主页", /* 1 */"备用主页", /* 2 */ "备用页" };
	public final static String MessageFileName = "Message.yml", ConfigFileName = "Config.yml",
			CommandFileName = "Command.yml", EconomyListConfigName = "EconomyList.yml", FormIDFileName = "FormID.yml",
			PlayerDataDirName = "Players", LanguageDirName = "language", SystemFileName = "System.xml",
			ShopName = "Shop.yml", ItemListName = "ItemList.yml", EnchantListName = "EnchantList.yml",
			NBTName = "MyNBT.yml";
	private static Activate activate;
	private MyEconomy economy;
	private EconomyManage money;
	private LinkedHashMap<String, MyPlayer> Players;
	protected FormID FormID;
	protected Message message;
	protected Config config, CommandConfig, ShopConfig, ItemListConfig, EnchantListConfig, NBTConfig;
	/**
	 * 默认要加载的配置文件，这些文件将会被用于与插件自带数据匹配
	 */
	protected static final String[] loadFile = { ConfigFileName, CommandFileName };
	/**
	 * 插件基础配置文件
	 */
	protected static final String[] defaultFile = { CommandFileName, MessageFileName };
	/**
	 * 只加载一次的数据
	 */
	protected static final String[] ForOnce = { ConfigFileName, EnchantListName, ItemListName };
	protected static final String[] Mkdir = { PlayerDataDirName };
	private ItemList items;
	private EnchantList enchants;

	/**
	 * 插件数据的集合类
	 *
	 * @param kis
	 */
	public Activate(Breast kis) {
		activate = this;
		mis = kis;
		FormID = new FormID();
		Players = new LinkedHashMap<>();
		if ((resCheck = new ResCheck(this).start()) == null)
			return;
		money = new EconomyManage();
		ShopConfig = new Config(new File(mis.getDataFolder(), ShopName), Config.YAML);
		EnchantListConfig = new Config(new File(mis.getDataFolder(), EnchantListName), Config.YAML);
		ItemListConfig = new Config(new File(mis.getDataFolder(), ItemListName), Config.YAML);
		Plugin plugin = Server.getInstance().getPluginManager().getPlugin(EconomyAPI.Name);
		if (plugin != null)
			money.addEconomyAPI(new EconomyAPI(this));
		economy = money.getEconomy(config.getString("默认货币"));
		if (config.getBoolean("检查更新"))
			(new Update(kis)).start();
		items = new ItemList(this);
		kis.getServer().getCommandMap().register(kis.getName(), new MyCommand(this));
		enchants = new EnchantList(this);
		NBTConfig = new Config(new File(mis.getDataFolder(), NBTName), Config.YAML);
		kis.getLogger().info(message.getMessage("插件启动", "{loadTime}",
				(float) Duration.between(mis.loadTime, Instant.now()).toMillis() + "ms") + "-Delte");
	}

	/**
	 * 获取我的NBT列表的配置文件
	 * 
	 * @return
	 */
	public Config getNBTConfig() {
		return NBTConfig;
	}

	/**
	 * 获取附魔列表的管理器
	 * 
	 * @return
	 */
	public EnchantList getEnchants() {
		return enchants;
	}

	/**
	 * 获取物品列表的管理器
	 * 
	 * @return
	 */
	public ItemList getItems() {
		return items;
	}

	/**
	 * 获取商店的配置文件
	 * 
	 * @return
	 */
	public Config getShopConfig() {
		return ShopConfig;
	}

	/**
	 * 返回附魔列表的配置文件
	 * 
	 * @return
	 */
	public Config getEnchantListConfig() {
		return EnchantListConfig;
	}

	/**
	 * 返回命令列表的配置文件
	 * 
	 * @return
	 */
	public Config getCommandConfig() {
		return CommandConfig;
	}

	/**
	 * 返回物品列表的配置文件
	 * 
	 * @return
	 */
	public Config getItemListConfig() {
		return ItemListConfig;
	}

	/**
	 * 返回一个默认的玩家数据
	 *
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getPlayerConfig() throws Exception {
		return resCheck.yaml.loadAs(Utils.readFile(getClass().getResourceAsStream("/resources/player.yml")), Map.class);
	}

	/**
	 * 得到默认经济插件
	 *
	 * @reaturn
	 */
	public MyEconomy getEconomy() {
		return economy;
	}

	/**
	 * 设置默认经济插件
	 *
	 * @param EconomyName
	 */
	public void setEconomy(String EconomyName) {
		if (money.supportEconomy(EconomyName))
			this.economy = money.getEconomy(EconomyName);
	}

	/**
	 * 获取插奸指令
	 * 
	 * @return
	 */
	public String[] getCommand() {
		Object obj = CommandConfig.get("Command");
		List<String> list = obj != null && obj instanceof List ? (List<String>) obj
				: obj instanceof Map ? new ArrayList<>(((Map<String, Object>) obj).keySet()) : new ArrayList<>();
		String[] strings = new String[list.size()];
		for (int i = 0; i < list.size(); i++)
			strings[i] = list.get(i);
		return strings;
	}

	/**
	 * 得到插件名称
	 *
	 * @return
	 */
	public String getName() {
		return mis.getName();
	}

	/**
	 * 得到插件主类
	 *
	 * @return
	 */
	public PluginBase getPluginBase() {
		return mis;
	}

	/**
	 * 删除玩家数据
	 *
	 * @param player
	 */
	public void removePlayers(Player player) {
		removePlayers(player.getName());
	}

	/**
	 * 删除玩家数据
	 *
	 * @param player
	 */
	public void removePlayers(String player) {
		if (Players.containsKey(player)) {
			Config config = Players.get(player).getConfig();
			config.set("QuitTime", Tool.getDate() + " " + Tool.getTime());
			config.save();
			Players.remove(player);
		}
	}

	/**
	 * 设置玩家数据
	 *
	 * @param player
	 * @return
	 */
	public void setPlayers(Player player, MyPlayer myPlayer) {
		if (!Players.containsKey(player.getName()))
			Players.put(player.getName(), myPlayer);
		myPlayer = Players.get(player.getName());
		myPlayer.setPlayer(player);
	}

	/**
	 * 设置玩家数据
	 *
	 * @param player
	 * @return
	 */
	public MyPlayer getPlayers(Player player) {
		return isPlayers(player.getName()) ? Players.get(player.getName()) : null;
	}

	/**
	 * 设置玩家数据
	 *
	 * @param player
	 * @return
	 */
	public MyPlayer getPlayers(String player) {
		return isPlayers(player) ? Players.get(player) : null;
	}

	/**
	 * 玩家数据是否存在
	 *
	 * @param player
	 * @return
	 */
	public boolean isPlayers(Player player) {
		return Players.containsKey(player.getName());
	}

	/**
	 * 玩家数据是否存在
	 *
	 * @param player
	 * @return
	 */
	public boolean isPlayers(String player) {
		return Players.containsKey(player);
	}

	/**
	 * 得到玩家数据
	 *
	 * @return
	 */
	public LinkedHashMap<String, MyPlayer> getPlayers() {
		return new LinkedHashMap<>(Players);
	}

	/**
	 * 返回经济支持管理器</br>
	 * Return to the economic support manager
	 *
	 * @return
	 */
	public EconomyManage getEconomyManage() {
		return money;
	}

	/**
	 * 得到ID类
	 *
	 * @return
	 */
	public FormID getFormID() {
		return FormID;
	}

	/**
	 * 得到语言类
	 *
	 * @return
	 */
	public Message getMessage() {
		return message;
	}

	/**
	 * 对外接口
	 *
	 * @return
	 */
	public static Activate getActivate() {
		return activate;
	}

	/**
	 * 返回EconomyAPI货币的名称
	 *
	 * @return
	 */
	public String getMoneyName() {
		return economy == null ? config.getString("货币名称") : economy.getMoneyName();
	}

	/**
	 * 得到MostBrain主配置文件
	 *
	 * @return
	 */
	public Config getConfig() {
		return config;
	}

	/**
	 * 判断玩家是否是管理员
	 * 
	 * @param player
	 * @return
	 */
	public boolean isAdmin(Player player) {
		return isAdmin(player.getName());
	}

	/**
	 * 判断玩家是否是管理员
	 * 
	 * @param player
	 * @return
	 */
	public boolean isAdmin(CommandSender player) {
		return isAdmin(player.getName());
	}

	/**
	 * 判断玩家是否是管理员
	 * 
	 * @param player
	 * @return
	 */
	public boolean isAdmin(String player) {
		if (config.getBoolean("astrictAdmin"))
			return config.getStringList("Admin").contains(player);
		return config.getStringList("Admin").contains(player) || Server.getInstance().isOp(player);
	}
}
