package main;

import main.Actions.Action;
import main.GUI.GUI;
import main.Strategies.RunLoop;
import org.parabot.core.Context;
import org.parabot.environment.api.interfaces.Paintable;
import org.parabot.environment.scripts.Category;
import org.parabot.environment.scripts.Script;
import org.parabot.environment.scripts.ScriptManifest;
import org.parabot.environment.scripts.framework.Strategy;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by SRH on 1/9/2018.
 * Welcome to AIO AIO - ScriptFactory. Make your own scripts!
 */


@ScriptManifest(author = "Before", name = "Script Factory 1.1", category = Category.OTHER, version = 1.1, description = "Create your own scripts!", servers = "Any")
public class Core extends Script implements Paintable {

    private VarsMethods vars = new VarsMethods();
    private ArrayList<Action> actions = new ArrayList<>();
    ArrayList<Strategy> strategies = new ArrayList<>();

    private GUI gui;

    @Override
    public boolean onExecute() {
        gui = new GUI(actions);

        gui.setVisible(true);

        while (gui.isShowing())
        {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (!gui.scriptStarted)
        {
            Context.getInstance().getRunningScript().setState(STATE_STOPPED);
        }

        strategies.add(new RunLoop(actions, vars));
        provide(strategies);

        return true;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.GREEN);

        g.setFont(new Font("Cordia New", Font.PLAIN, 16));
        g.drawString("Script Factory", 580, 330);
        g.setFont(new Font("Cordia New", Font.PLAIN, 12));
        g.drawString("Currently executing: ", 580, 347);
        g.drawString(vars.currentAction, 580, 360);
    }
}