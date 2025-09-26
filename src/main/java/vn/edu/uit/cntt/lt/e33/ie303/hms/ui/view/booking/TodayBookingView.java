package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.booking;

import java.awt.*;
import javax.swing.*;

public class TodayBookingView extends JPanel {
    public TodayBookingView() {
        super(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Welcome to UIT Hotel Management Pro", SwingConstants.CENTER);
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JPanel contentPanel = new JPanel(new GridLayout(2, 3, 20, 20));

        // Create quick action cards
        contentPanel.add(createQuickActionCard("New Booking", "Create a new room booking"));
        contentPanel.add(createQuickActionCard("Check In", "Check in a guest"));
        contentPanel.add(createQuickActionCard("Check Out", "Check out a guest"));
        contentPanel.add(createQuickActionCard("Room Status", "View room availability"));
        contentPanel.add(createQuickActionCard("Guest Management", "Manage guest information"));
        contentPanel.add(createQuickActionCard("Reports", "Generate reports"));

        add(titleLabel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }



    private JPanel createQuickActionCard(String title, String description) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));

        JLabel descLabel = new JLabel("<html><div style='text-align: center'>" + description + "</div></html>", SwingConstants.CENTER);
        descLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        descLabel.setForeground(Color.GRAY);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(descLabel, BorderLayout.CENTER);

        return card;
    }
}