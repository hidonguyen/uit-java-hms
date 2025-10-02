package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.booking;

import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.TodayBookingDto;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.presenter.BookingPresenter;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.Constants;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.UIUtils;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.ui.WrapLayout;

public class TodayBookingView extends JPanel {
    private BookingPresenter presenter;

    private JPanel cardsPanel;

    private List<TodayBookingDto> bookings;

    public TodayBookingView() {
        super(new BorderLayout());

        initializeComponents();
    }

    public void setPresenter(BookingPresenter presenter) {
        this.presenter = presenter;
    }

    public void setBookings(List<TodayBookingDto> bookings) {
        this.bookings = bookings;
        cardsPanel.removeAll();
        for (TodayBookingDto booking : this.bookings) {
            cardsPanel.add(createRoomCard(booking));
        }
        cardsPanel.revalidate();
        cardsPanel.repaint();
    }

    private void initializeComponents() {
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        setupLayout();
    }

    private void setupLayout() {
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        JPanel mainPanel = createMainPanel();
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel titleLabel = new JLabel("Today Bookings");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(UIUtils.TEXT_COLOR);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JPanel searchPanel = createHeaderButtonPanel();
        headerPanel.add(searchPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createHeaderButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonPanel.setBackground(UIUtils.TEXT_COLOR);

        JButton newBookingBtn = new JButton("+ New Booking");
        UIUtils.styleButton(newBookingBtn, UIUtils.ButtonStyle.PRIMARY);
        newBookingBtn.setPreferredSize(new Dimension(150, 40));
        newBookingBtn.addActionListener(_ -> presenter.onNewBookingClicked());

        buttonPanel.add(newBookingBtn);
        return buttonPanel;
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        cardsPanel = new JPanel();
        cardsPanel.setLayout(new WrapLayout(FlowLayout.LEFT, 20, 20));
        cardsPanel.setBackground(Color.WHITE);
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JScrollPane roomCardsScrollPane = new JScrollPane(cardsPanel);
        roomCardsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        roomCardsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        roomCardsScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        mainPanel.add(roomCardsScrollPane, BorderLayout.CENTER);

        return mainPanel;
    }

    private JPanel createRoomCard(TodayBookingDto booking) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(UIUtils.CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIUtils.BORDER_COLOR, 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.setPreferredSize(new Dimension(300, 260));
        card.setMinimumSize(new Dimension(300, 260));
        card.setMaximumSize(new Dimension(300, 260));

        JPanel roomInfo = new JPanel(new BorderLayout());
        roomInfo.setOpaque(false);
        roomInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
        roomInfo.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));

        JLabel roomLabel = new JLabel("Room " + booking.getRoomName());
        roomLabel.setForeground(new Color(0, 123, 255));
        roomLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel roomTypeLabel = new JLabel(booking.getRoomTypeName());

        roomInfo.add(roomLabel, BorderLayout.WEST);
        roomInfo.add(roomTypeLabel, BorderLayout.EAST);

        JPanel guestInfoPanel = new JPanel(new BorderLayout());
        guestInfoPanel.setOpaque(false);
        guestInfoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        guestInfoPanel.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
        guestInfoPanel.add(new JLabel(booking.getPrimaryGuestName()), BorderLayout.WEST);
        guestInfoPanel.add(new JLabel(booking.getPrimaryGuestPhone()), BorderLayout.EAST);

        JPanel numberOfGuests = new JPanel(new BorderLayout());
        numberOfGuests.setOpaque(false);
        numberOfGuests.setAlignmentX(Component.LEFT_ALIGNMENT);
        numberOfGuests.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
        numberOfGuests.add(new JLabel("Adults: " + booking.getNumAdults()), BorderLayout.WEST);
        numberOfGuests.add(new JLabel("Children: " + booking.getNumChildren()), BorderLayout.EAST);

        JPanel checkInTimePanel = createInfoRow("Check-in:", booking.getCheckin().format(DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm)));

        NumberFormat money = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        JPanel roomChargePanel = createInfoRow("Room Charges:", money.format(booking.getTotalRoomCharges()));
        JLabel roomChargeValue = (JLabel) roomChargePanel.getComponent(1);
        roomChargeValue.setForeground(new Color(0, 150, 0));

        JPanel serviceChargePanel = createInfoRow("Service Charges:",
                money.format(booking.getTotalServiceCharges()));
        JLabel serviceChargeValue = (JLabel) serviceChargePanel.getComponent(1);
        serviceChargeValue.setForeground(new Color(0, 150, 0));

        card.add(roomInfo);
        card.add(guestInfoPanel);
        card.add(numberOfGuests);
        card.add(checkInTimePanel);
        card.add(roomChargePanel);
        card.add(serviceChargePanel);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (presenter != null) {
                    presenter.onBookingCardClicked(booking.getId());
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(0, 123, 255), 2),
                        BorderFactory.createEmptyBorder(14, 14, 14, 14)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                        BorderFactory.createEmptyBorder(15, 15, 15, 15)));
            }
        });

        return card;
    }

    private JPanel createInfoRow(String label, String value) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));

        JLabel labelComp = new JLabel(label);
        JLabel valueComp = new JLabel(value);

        panel.add(labelComp, BorderLayout.WEST);
        panel.add(valueComp, BorderLayout.EAST);

        return panel;
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this,
                "<html><div style='padding: 10px; font-family: Segoe UI; font-size: 14px;'>" + message
                        + "</div></html>",
                "Error - " + Constants.ErrorTitle.BOOKING,
                JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this,
                "<html><div style='padding: 10px; font-family: Segoe UI; font-size: 14px;'>" + message
                        + "</div></html>",
                "Success - " + Constants.ErrorTitle.BOOKING,
                JOptionPane.INFORMATION_MESSAGE);
    }
}