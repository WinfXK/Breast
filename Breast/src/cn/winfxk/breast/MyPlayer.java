package cn.winfxk.breast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.utils.Config;
import cn.winfxk.breast.form.FormBase;
import cn.winfxk.breast.tool.Tool;

/**
 * @author Winfxk
 */
public class MyPlayer {
	private static Activate ac = Activate.getActivate();
	public Config config;
	private Player player;
	public FormBase form;
	public int ID = 0;
	public boolean isTrade = false;
	public Player TradePlayer;

	/**
	 * 记录存储玩家的一些数据
	 *
	 * @param player
	 */
	public MyPlayer(Player player) {
		this.player = player;
		config = getConfig(getName());
		config = ac.resCheck.Check(this);
		config.set("name", player.getName());
		config.save();
	}

	/**
	 * 清空正在交易的物品
	 * 
	 * @return
	 */
	public boolean clearitem() {
		config.set("Items", new HashMap<>());
		return config.save();
	}

	/**
	 * 获取正在交易的物品列表
	 * 
	 * @return
	 */
	public List<Item> getItems() {
		Object obj = config.get("Items");
		Map<String, Object> map = obj != null && obj instanceof Map ? (HashMap<String, Object>) obj : new HashMap<>();
		List<Item> list = new ArrayList<>();
		for (Object object : map.values())
			list.add(Tool.loadItem((Map<String, Object>) object));
		return list;
	}

	/**
	 * 判断一个玩家是否缓存了物品
	 * 
	 * @return
	 */
	public boolean isSaveItem() {
		Object obj = config.get("Items");
		Map<String, Object> map = obj != null && obj instanceof Map ? (HashMap<String, Object>) obj : new HashMap<>();
		return map.size() > 0;
	}

	/**
	 * 加载玩家交易缓存的数据，并且吧缓存的物品给玩家
	 * 
	 * @return
	 */
	public boolean reloadItem() {
		Object obj = config.get("Items");
		Map<String, Object> map = obj != null && obj instanceof Map ? (HashMap<String, Object>) obj : new HashMap<>();
		Item item;
		for (Object object : map.values()) {
			item = Tool.loadItem((Map<String, Object>) object);
			player.getInventory().addItem(item);
		}
		config.set("Items", new HashMap<>());
		return config.save();
	}

	/**
	 * 缓存玩家交易中心的物品
	 * 
	 * @param list
	 */
	public boolean saveItem(List<Item> list) {
		Map<String, Object> map = new HashMap<>();
		for (Item item : list)
			map.put(getsaveitemKey(map, 1), Tool.saveItem(item));
		config.set("Items", map);
		return config.save();
	}

	/**
	 * 用于获取缓存交易中的物品的随机Key
	 * 
	 * @param map
	 * @param JJLength
	 * @return
	 */
	private String getsaveitemKey(Map<String, Object> map, int JJLength) {
		String string = "";
		for (int i = 0; i < JJLength; i++)
			string += Tool.getRandString();
		if (map.containsKey(string))
			return getsaveitemKey(map, JJLength + 1);
		return string;
	}

	/**
	 * 判断玩家是否是管理员
	 * 
	 * @return
	 */
	public boolean isAdmin() {
		return ac.isAdmin(player);
	}

	public Player getPlayer() {
		return player;
	}

	/**
	 * 获取逗比玩家的金币数量
	 *
	 * @return
	 */
	public double getMoney() {
		return ac.getEconomy().getMoney(player);
	}

	/**
	 * 获取逗比玩家的金币数量
	 *
	 * @return
	 */
	public static double getMoney(String player) {
		return Activate.getActivate().getEconomy().getMoney(player);
	}

	public Config getConfig() {
		return config;
	}

	/**
	 * 得到一个玩家的配置文件对象
	 *
	 * @param player 玩家名称
	 * @return
	 */
	public static Config getConfig(String player) {
		return new Config(getFile(player), Config.YAML);
	}

	/**
	 * 得到一个玩家配置文件的文件对象
	 *
	 * @return
	 */
	public File getFile() {
		return new File(new File(ac.getPluginBase().getDataFolder(), Activate.PlayerDataDirName),
				player.getName() + ".yml");
	}

	/**
	 * 得到一个玩家配置文件的文件对象
	 *
	 * @param player 玩家名称
	 * @return
	 */
	public static File getFile(String player) {
		return new File(new File(Activate.getActivate().getPluginBase().getDataFolder(), Activate.PlayerDataDirName),
				player + ".yml");
	}

	public String getName() {
		return player.getName();
	}

	/**
	 * 判断玩家的配置文件是否存在
	 *
	 * @param player
	 * @return
	 */
	public static boolean isPlayer(String player) {
		File file = getFile(player);
		return file.exists();
	}

	/**
	 * 将一条信息打包给玩家
	 *
	 * @param player
	 * @param Message
	 * @return 若玩家在线将返回True，否则返回False
	 */
	public static boolean sendMessage(String player, String Message) {
		if (player == null || Message == null || player.isEmpty() || Message.isEmpty() || !isPlayer(player))
			return false;
		if (Activate.getActivate().isPlayers(player)) {
			Activate.getActivate().getPlayers(player).player.sendMessage(Message);
			return true;
		}
		Config config = getConfig(player);
		Object obj = config.get("Message");
		List<Object> list = obj == null || !(obj instanceof List) ? new ArrayList<>() : (ArrayList<Object>) obj;
		list.add(Message);
		config.set("Message", list);
		config.save();
		return false;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
