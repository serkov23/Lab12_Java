package lab12;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Vector;

public class MyFrame extends JFrame {
    private final int N = 10, M = 15;
    private JTabbedPane tabbedPane = new JTabbedPane();

    MyFrame() {
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 250);
        task1();
        task2();
        task3();
    }

    private void task3() {
        JPanel panel = new JPanel(new GridLayout(5, 1));
        tabbedPane.addTab("Task 3", panel);
        Vector<JRadioButton> buttons = new Vector<>();
        buttons.add(new JRadioButton("1 group"));
        buttons.add(new JRadioButton("2 group"));
        buttons.add(new JRadioButton("3 group"));
        buttons.add(new JRadioButton("4 group"));
        var group = new ButtonGroup();
        for (var i : buttons) {
            group.add(i);
            panel.add(i);
        }
        Icon icon = createIcon(Color.MAGENTA);
        Icon prIcon = createIcon(Color.CYAN);
        Icon rIcon = createIcon(Color.BLUE);
        Icon selIcon = createIcon(Color.GRAY);
        Icon rsIcon = createIcon(Color.GREEN);
        for (var i : buttons) {
            i.setIcon(icon);
            i.setPressedIcon(prIcon);
            i.setRolloverIcon(rIcon);
            i.setSelectedIcon(selIcon);
            i.setRolloverSelectedIcon(rsIcon);
        }
    }

    private Icon createIcon(Color color) {
        Image defImg = new BufferedImage(10, 10, BufferedImage.TYPE_3BYTE_BGR);
        Graphics gr = defImg.getGraphics();
        gr.setColor(color);
        gr.fillRoundRect(2, 2, 6, 6, 2, 2);
        return new ImageIcon(defImg);
    }

    private void task1() {

        DefaultListModel model1 = new DefaultListModel(), model2 = new DefaultListModel();
        JList list1 = new JList(), list2 = new JList();
        JButton button1 = new JButton(">"), button2 = new JButton("<");
        tabbedPane.addTab("Task1", createPanelForTask1(list1, list2, model1, model2, button1, button2));
        add(tabbedPane);
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            model1.addElement(random.nextInt(123));
            model2.addElement(random.nextInt(123));
        }
        button1.addActionListener(new ButtonListenerForTask1(list1, model1, model2));
        button2.addActionListener(new ButtonListenerForTask1(list2, model2, model1));
    }

    private void task2() {
        JPanel panel = new JPanel(new GridLayout(N, M));
        tabbedPane.addTab("Task2", panel);
        MouseListener listener = new MouseAdapter() {
            Button lastPressed = null;
            String text = null;
            Color prevBackground = null;

            @Override
            public void mousePressed(MouseEvent e) {
                lastPressed = (Button) e.getComponent();
                text = lastPressed.getLabel();
                lastPressed.setLabel("Clicked!");

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (lastPressed == null)
                    return;
                lastPressed.setLabel(text);
                text = null;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                prevBackground = e.getComponent().getBackground();
                e.getComponent().setBackground(Color.CYAN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (prevBackground == null) {
                    return;
                }
                e.getComponent().setBackground(prevBackground);
                prevBackground = null;
            }
        };
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                Button b = new Button(Integer.toString(i * M + j));
                b.addMouseListener(listener);
                panel.add(b);
            }
        }
    }

    private JPanel createPanelForTask1(JList list1, JList list2, DefaultListModel model1, DefaultListModel model2, JButton button1, JButton button2) {
        JPanel panel = new JPanel(new BorderLayout());
        list1.setModel(model1);
        list2.setModel(model2);
        panel.add(list1, BorderLayout.WEST);
        panel.add(list2, BorderLayout.EAST);
        JPanel additionalPanel = new JPanel(new BorderLayout());
        additionalPanel.add(button1, BorderLayout.NORTH);
        additionalPanel.add(button2, BorderLayout.SOUTH);
        panel.add(additionalPanel, BorderLayout.CENTER);
        return panel;
    }

    private void errorMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private class ButtonListenerForTask1 implements ActionListener {

        private JList list1;
        private DefaultListModel model2;
        private DefaultListModel model1;

        public ButtonListenerForTask1(JList list1, DefaultListModel model1, DefaultListModel model2) {
            this.list1 = list1;
            this.model2 = model2;
            this.model1 = model1;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int[] ind = list1.getSelectedIndices();
            for (int i : ind)
                model2.addElement(model1.getElementAt(i));
            for (int i = ind.length - 1; i >= 0; i--)
                model1.remove(ind[i]);
        }
    }


}