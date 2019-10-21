/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import sygfx.border.LineBorder;
import sygfx.ui.Button;
import sygfx.ui.Container;
import sygfx.ui.Label;
import sygfx.ui.Panel;
import sygfx.ui.layout.BorderLayout;
import sygfx.ui.layout.FlowLayout;
import sygfx.util.Anchor;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class UserInterfaceDesigner {
    public static void design(UICanvas uiCanvas){
        uiCanvas.setLayout(new FlowLayout(FlowLayout.HORIZONTAL, Anchor.CENTER, 0, 0));
        designMenu(uiCanvas);
    }
    
    public static void designMenu(UICanvas uiCanvas){
        Panel menu_panel = new Panel();
        menu_panel.setBackground(Color.gray);
        menu_panel.setLayout(new FlowLayout(FlowLayout.HORIZONTAL, Anchor.CENTER, 0, 0));
        menu_panel.setPreferredSize(uiCanvas.getPreferredSize());
        
        Panel center_panel = new Panel();
        center_panel.setLayout(new BorderLayout());
        
        Panel upperTextPanel = new Panel();
        upperTextPanel.setLayout(new FlowLayout(FlowLayout.HORIZONTAL, Anchor.NORTH, 20, 30));
        Label upperLabel = new Label("Welcome to Hatman!");
        upperLabel.setForeground(Color.green);
        upperLabel.setFont(new Font("Arial", Font.BOLD, 30));
        upperTextPanel.add(upperLabel);
        
        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.VERTICAL, Anchor.NORTH));
        for(int i = 0; i < 4; i++){
            Panel p = new Panel();
            p.setBorder(new LineBorder(2, Color.black));
            Color color = new Color(204, 204 - i * 20, 0);
            p.setBackground(color);
            Button button = new Button(160, 40, "Button");
            button.setFont(new Font("Arial", Font.PLAIN, 16));
            button.setEnabled(false);
            if(i == 0){
                button.setText("Start");
                button.setEnabled(true);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        menu_panel.setVisible(false);
                        uiCanvas.getGameEnvironment().reset();
                    }
                });
            }
            buttonPanel.add(button);
        }
        
        Panel lowerPanel = new Panel();
        lowerPanel.setLayout(new FlowLayout(FlowLayout.VERTICAL, Anchor.NORTH));
        
        Label difficulty = new Label("Difficulty");
        
        Panel lowerButtonPanel = new Panel();
        for(int i = 0; i < 3; i++){
            Button b = new Button(160, 40, "");
            b.setFont(new Font("Arial", Font.PLAIN, 16));
            if(i == 0){
                b.setText("Easy");
            }
            if(i == 1){
                b.setText("Medium");
            }
            if(i == 2){
                b.setText("Hard");
            }
            lowerButtonPanel.add(b);
        }
        
        lowerPanel.add(difficulty);
        lowerPanel.add(lowerButtonPanel);
        
        center_panel.add(upperTextPanel, BorderLayout.NORTH);
        center_panel.add(buttonPanel, BorderLayout.CENTER);
        center_panel.add(lowerPanel, BorderLayout.SOUTH);
        
        menu_panel.add(center_panel);
        uiCanvas.add(menu_panel);
    }
}
