package cn.winfxk.breast.cmd;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.winfxk.breast.Activate;
import cn.winfxk.breast.Message;
import cn.winfxk.breast.MyPlayer;
import cn.winfxk.breast.form.MainForm;
import cn.winfxk.breast.form.more.SelectItem;

/**
 * 命令处理器
 * 
 * @Createdate 2020/05/13 18:38:14
 * @author Winfxk
 */
public class MyCommand extends Command {
	private Activate ac;
	private Message msg;

	public MyCommand(Activate ac) {
		super(ac.getPluginBase().getName().toLowerCase(), ac.getPluginBase().getName().toLowerCase() + " Command",
				"/" + ac.getPluginBase().getName().toLowerCase(), ac.getCommands("Command", "Main"));
		this.ac = ac;
		msg = ac.getMessage();
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (!sender.hasPermission("Breast.Command.main")) {
			sender.sendMessage(msg.getMessage("权限不足"));
			return true;
		}
		if (!sender.isPlayer()) {
			sender.sendMessage(msg.getMessage("NotPlayer"));
			return true;
		}
		MyPlayer myPlayer = ac.getPlayers(sender.getName());
		if (args == null || args.length <= 0) {
			myPlayer.form = new MainForm(myPlayer.getPlayer(), null);
			return myPlayer.form.MakeMain();
		}
		switch (args[0].toLowerCase()) {
		case "up":
			myPlayer.form = new SelectItem(myPlayer.getPlayer(), null);
			break;
		case "main":
		case "m":
		default:
			myPlayer.form = new MainForm(myPlayer.getPlayer(), null);
		}
		return myPlayer.form.MakeMain();
	}
}
