package com.gamesense.client.command;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.gamesense.client.command.commands.*;

import java.util.ArrayList;

public class CommandManager{
	private static ArrayList<Command> commands;
	boolean validCommand;

	public static void initCommands(){
		commands = new ArrayList<>();
		addCommand(new AutoGgCommand());
		addCommand(new AutoReplyCommand());
		addCommand(new BindCommand());
		addCommand(new CmdsCommand());
		addCommand(new DisableAllCommand());
		addCommand(new DrawnCommand());
		addCommand(new EnemyCommand());
		addCommand(new FixGuiCommand());
		addCommand(new FontCommand());
		addCommand(new FriendCommand());
		addCommand(new MacroCommand());
		addCommand(new ModsCommand());
		addCommand(new OpenFolderCommand());
		addCommand(new PrefixCommand());
		addCommand(new SaveConfigCommand());
		addCommand(new SetSettingCommand());
		addCommand(new ToggleCommand());
		addCommand(new VanishCommand());
	}

	public static void addCommand(Command command){
		commands.add(command);
	}

	public static ArrayList<Command> getCommands(){
		return commands;
	}

	public void callCommand(String input){
		String[] split = input.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // Split by every space if it isn't surrounded by quotes // credit 086/KAMI
		String command = split[0];
		String args = input.substring(command.length()).trim();
		validCommand = false;
		commands.forEach(c -> {
			for (String s : c.getAlias()){
				if (s.equalsIgnoreCase(command)){
					validCommand = true;
					try{
						c.onCommand(args, args.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"));
					} catch (Exception e){
						Command.sendClientMessage(ChatFormatting.GRAY + c.getSyntax());
					}
				}
			}
		});
		if (!validCommand) Command.sendClientMessage(ChatFormatting.GRAY + "Unknown command! Type " + Command.getPrefix() + "help for a list of commands!");
	}
}