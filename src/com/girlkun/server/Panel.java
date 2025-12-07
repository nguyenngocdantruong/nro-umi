package com.girlkun.server;

import com.girlkun.utils.Logger;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Panel extends JPanel implements ActionListener {

    private JButton btnBaoTri;

    public Panel() {

        // PANEL này phải dùng GridBagLayout để căn giữa
        setLayout(new GridBagLayout());
        setBackground(new Color(35, 35, 35));

        // panel chứa nút (để nút to đẹp hơn)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 1));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        btnBaoTri = new JButton("Bảo trì");
        btnBaoTri.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnBaoTri.setBackground(new Color(65, 65, 65));
        btnBaoTri.setForeground(Color.WHITE);
        btnBaoTri.setFocusPainted(false);
        btnBaoTri.setPreferredSize(new Dimension(180, 40));
        btnBaoTri.addActionListener(this);

        buttonPanel.add(btnBaoTri);

        // Add panel nút vào giữa
        add(buttonPanel, new GridBagConstraints());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBaoTri) {
            Maintenance.gI().start(15);
            Logger.error("Tiến hành bảo trì\n");
        }
    }
}
