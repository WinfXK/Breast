package cn.winfxk.breast.form.t;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.item.Item;
import cn.winfxk.breast.form.FormBase;
import cn.winfxk.breast.tool.CustomForm;
import cn.winfxk.breast.tool.SimpleForm;
import cn.winfxk.breast.tool.Tool;

/**
 * 实时交易选择物品界面
 * 
 * @Createdate 2020/05/13 15:52:39
 * @author Winfxk
 */
public class SelectItem extends FormBase {
	private Map<Integer, Item> items;
	private List<Integer> indexs = new ArrayList<>();
	private List<Item> list = new ArrayList<>(), oflist;
	private boolean isSend = false;
	private Player ofPlayer;

	/**
	 * 这是接受请求的人的接口
	 * 
	 * @param player   接受请求的玩家对象
	 * @param upForm   三个界面
	 * @param ofPlayer 发送请求的玩家对象
	 * @param ofList   发送请求的玩家的交易内容
	 */
	public SelectItem(Player player, FormBase upForm, Player ofPlayer, List<Item> ofList) {
		super(player, upForm);
		setSon("TransactionOfSelectItem");
		oflist = ofList;
		this.ofPlayer = ofPlayer;
		isSend = true;
	}

	/**
	 * 这是发请求的人的接口
	 * 
	 * @param player 发送请求的玩家对象
	 * @param upForm 三个界面
	 */
	public SelectItem(Player player, FormBase upForm) {
		super(player, upForm);
		setSon("TransactionOfSelectItem");
		if (myPlayer.isSaveItem()) {
			myPlayer.reloadItem();
			player.sendMessage(getString("loadItemOK"));
		}
	}

	@Override
	public boolean MakeMain() {
		items = player.getInventory().getContents();
		SimpleForm form = new SimpleForm(getID(), getTitle(), getContent());
		Item item;
		String[] KK = { "{Player}", "{Money}", "{ItemName}", "{ItemID}", "{ItemDamage}", "{ItemCount}", "{ItemPath}" };
		for (Integer i : items.keySet()) {
			item = items.get(i);
			if (item.getId() == 0)
				continue;
			form.addButton(getString("ItemFormat", KK, new Object[] { player.getName(), myPlayer.getMoney(),
					itemList.getName(item), item.getId(), item.getDamage(), item.count, itemList.getPath(item) }));
			indexs.add(i);
		}
		if (indexs.size() <= 0) {
			player.sendMessage(getString("notItem"));
			return isBack() && wasClosed();
		}
		if (list.size() > 0) {
			listKey.add("sok");
			form.addButton(getString("ConfirmItems"));
		}
		listKey.add("bk");
		form.addButton(getBack());
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean wasClosed() {
		if (list.size() > 0)
			for (Item item : list)
				player.getInventory().addItem(item);
		if (oflist != null && ofPlayer != null && oflist.size() > 0) {
			for (Item item : oflist)
				ofPlayer.getInventory().addItem(item);
			ofPlayer.sendMessage(getString("wasClosed"));
		}
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		int ID = getSimple(data).getClickedButtonId();
		if (indexs.size() < ID)
			return setForm(new SelectCount(player, this, items.get(indexs.get(ID)))).make();
		switch (listKey.get(indexs.size() - ID)) {
		case "sok":
			if (isSend) {
				myPlayer.saveItem(list);
				return setForm(new OpenedDealMsg(ofPlayer, oflist, upForm, player, list)).make();
			}
			return setForm(new Transaction(player, upForm, list)).make();
		default:
			return wasClosed();
		}
	}

	/**
	 * 玩家选择了一个物品 这个是选择数量的类
	 * 
	 * @Createdate 2020/05/13 16:18:51
	 * @author Winfxk
	 */
	private class SelectCount extends FormBase {
		private Item item;

		public SelectCount(Player player, SelectItem upForm, Item item) {
			super(player, upForm);
			this.item = item;
		}

		@Override
		public boolean MakeMain() {
			CustomForm form = new CustomForm(getID(), SelectItem.this.getTitle());
			form.addLabel(SelectItem.this.getContent());
			form.addSlider(SelectItem.this.getString("SelectCount"), 1, item.getCount(), 1);
			form.sendPlayer(player);
			return true;
		}

		@Override
		public boolean wasClosed() {
			return SelectItem.this.wasClosed();
		}

		@Override
		public boolean disMain(FormResponse data) {
			int Count = Tool.ObjToInt(getCustom(data).getSliderResponse(1));
			item.setCount(Count);
			player.getInventory().remove(item);
			list.add(item);
			player.sendMessage(SelectItem.this.getString("addItem"));
			return SelectItem.this.MakeMain();
		}
	}
}
