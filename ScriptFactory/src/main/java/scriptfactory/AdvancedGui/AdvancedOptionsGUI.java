package scriptfactory.AdvancedGui;

import scriptfactory.Actions.Action;
import scriptfactory.AdvancedGui.ScriptFactorySDN.ScriptFactorySDNGui;
import scriptfactory.Consumer;
import scriptfactory.GUI.EnterJButton;
import scriptfactory.NewGuis.UncommonActionGuiInfo;
import scriptfactory.VarsMethods;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import static scriptfactory.NewGuis.NewStatementGUI.addEscapeHotkey;
import static scriptfactory.VarsMethods.log;

public class AdvancedOptionsGUI extends JFrame {
    private JPanel tickSpeedPanel = new JPanel();
    private TipsAndTricksGUI tipsFrame = new TipsAndTricksGUI();
    private EnterJButton tipsAndTricksButton = new EnterJButton("Tips and tricks");
    private EnterJButton moveLineButton = new EnterJButton("Move line");
    private EnterJButton recoverPreviousScript = new EnterJButton("Recover previous script");
    private EnterJButton uncommonActionButton = new EnterJButton("Add uncommon actions");
    private EnterJButton premadeScriptsButton = new EnterJButton("Premade scripts");

    private ArrayList<Action> actions;
    private Consumer<Integer> updateTextfield;

    private JFrame moveLineFrame = new JFrame();
    private UncommonActionGuiInfo uncommonActionGui;
    private ScriptFactorySDNGui sdnGui;

    public AdvancedOptionsGUI(ArrayList<Action> actions, Consumer<Integer> updateTextfield, JTextField tickSpeedField) {
        this.actions = actions;
        this.updateTextfield = updateTextfield;

        setLayout(new GridLayout(2, 4, 15, 15));
        setTitle("Parabot.org Script Factory - Advanced Options");
        initButtons();

        generateTickSpeedPanel(tickSpeedField);
        add(tickSpeedPanel);

        add(tipsAndTricksButton);

        generateMoveLineFrame();
        add(moveLineButton);

        add(recoverPreviousScript);

        uncommonActionGui = new UncommonActionGuiInfo(actions, updateTextfield);
        add(uncommonActionButton);

        sdnGui = new ScriptFactorySDNGui();
        add(premadeScriptsButton);

        addEscapeHotkey(this);
        addEscapeHotkey(moveLineFrame);
        setMinimumSize(new Dimension(400, 250));
        revalidate();
        pack();
    }

    private void generateTickSpeedPanel(JTextField tickSpeedField) {
        tickSpeedPanel.setLayout(new GridLayout(2, 1));
        tickSpeedPanel.add(new JLabel("Change tick speed (ms):"));
        tickSpeedField.setColumns(8);
        tickSpeedField.setText("1200");
        tickSpeedPanel.add(tickSpeedField);
    }

    private void generateMoveLineFrame() {
        final JTextField lineToMove = new JTextField(6);
        final JTextField lineToInsertAbove = new JTextField(6);
        EnterJButton submitMove = new EnterJButton("Submit");

        moveLineFrame.setLayout(new GridLayout(5, 1, 5, 15));
        moveLineFrame.setMinimumSize(new Dimension(250, 400));
        moveLineFrame.setTitle("Move a script line");

        moveLineFrame.add(new JLabel("Enter the line # you wish to move: "));
        moveLineFrame.add(lineToMove);
        moveLineFrame.add(new JLabel("Enter the line # you wish to move it above:"));
        moveLineFrame.add(lineToInsertAbove);
        moveLineFrame.add(submitMove);

        moveLineFrame.pack();

        submitMove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent b) {
                int lineToMoveAsPint = VarsMethods.parsePint(lineToMove.getText());
                int lineToPlaceAboveAsPint = VarsMethods.parsePint(lineToInsertAbove.getText());

                Action removed = actions.remove(lineToMoveAsPint);
                if (lineToPlaceAboveAsPint <= lineToMoveAsPint)
                    actions.add(lineToPlaceAboveAsPint, removed);
                else if (lineToPlaceAboveAsPint > actions.size())
                    actions.add(removed);
                else
                    actions.add(lineToPlaceAboveAsPint - 1, removed);
                updateTextfield.accept(5);
                moveLineFrame.setVisible(false);
                log("Successfully moved line " + lineToMove.getText() + ".");
            }
        });

    }

    private void initButtons()
    {
        tipsAndTricksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent o) {
                tipsFrame.setVisible(true);
                AdvancedOptionsGUI.this.setVisible(false);
            }
        });
        moveLineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent o) {
                moveLineFrame.setVisible(true);
                AdvancedOptionsGUI.this.setVisible(false);
            }
        });
        recoverPreviousScript.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent o) {
                VarsMethods.loadscript(actions, new File(VarsMethods.CACHED_LOC));
                updateTextfield.accept(5);
                AdvancedOptionsGUI.this.setVisible(false);
            }
        });
        uncommonActionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent o) {
                uncommonActionGui.setVisible(true);
                AdvancedOptionsGUI.this.setVisible(false);
            }
        });
        premadeScriptsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent o) {
                sdnGui.setVisible(true);
                AdvancedOptionsGUI.this.setVisible(false);
            }
        });
    }

    public void killAllGuis() {
        moveLineFrame.setVisible(false);
        tipsFrame.setVisible(false);
        uncommonActionGui.setVisible(false);
        sdnGui.setVisible(false);
    }
}

