package ATM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class ATMSimulator {
    private BankAccount account;
    private JFrame mainFrame;
    private NumberFormat indonesiaFormat = NumberFormat.getNumberInstance(new Locale("id", "ID"));

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ATMSimulator().createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        JFrame createAccountFrame = new JFrame("Buat Akun Baru");
        createAccountFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createAccountFrame.setSize(400, 250);
        createAccountFrame.setLocationRelativeTo(null);
        createAccountFrame.setLayout(new GridLayout(4, 2, 5, 5));

        JTextField accNumberField = new JTextField();
        JTextField accHolderField = new JTextField();
        JTextField balanceField = new JTextField();
        JButton createButton = new JButton("Buat Akun");

        createAccountFrame.add(new JLabel("Nomor Akun:"));
        createAccountFrame.add(accNumberField);
        createAccountFrame.add(new JLabel("Nama Pemilik:"));
        createAccountFrame.add(accHolderField);
        createAccountFrame.add(new JLabel("Saldo Awal:"));
        createAccountFrame.add(balanceField);
        createAccountFrame.add(new JLabel());
        createAccountFrame.add(createButton);

        createButton.addActionListener(e -> {
            try {
                double initialBalance = indonesiaFormat.parse(balanceField.getText()).doubleValue();

                account = new BankAccount(
                        accNumberField.getText(),
                        accHolderField.getText(),
                        initialBalance);

                createAccountFrame.dispose();
                showMainMenu();
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(createAccountFrame,
                        "Format angka tidak valid! Contoh: 120.000 atau 150.500,75");
            }
        });

        createAccountFrame.setVisible(true);
    }

    private void showMainMenu() {
        mainFrame = new JFrame("ATM Simulator");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 300);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(new BorderLayout());
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        JLabel accNumberLabel = new JLabel("Nomor Akun: " + account.getAccountNumber());
        accNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel accHolderLabel = new JLabel("Pemilik: " + account.getAccountHolder());
        accHolderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoPanel.add(accNumberLabel);
        infoPanel.add(accHolderLabel);
        JPanel menuPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        String[] buttons = { "Deposit", "Tarik Tunai", "Cek Saldo", "Total Transaksi", "Keluar" };

        for (String text : buttons) {
            JButton btn = new JButton(text);
            btn.addActionListener(new MenuClickListener());
            menuPanel.add(btn);
        }

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        centerPanel.add(infoPanel, gbc);
        centerPanel.add(menuPanel, gbc);

        mainFrame.add(centerPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }

    private class MenuClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = ((JButton) e.getSource()).getText();

            switch (command) {
                case "Deposit":
                    handleDeposit();
                    break;

                case "Tarik Tunai":
                    handleWithdraw();
                    break;

                case "Cek Saldo":
                    showBalance();
                    break;

                case "Total Transaksi":
                    showTransactionCount();
                    break;

                case "Keluar":
                    exitApplication();
                    break;
            }
        }

        private void handleDeposit() {
            String depositAmount = JOptionPane.showInputDialog(mainFrame,
                    "Masukkan jumlah deposit (contoh: 50.000 atau 75.500,25):");
            if (depositAmount != null) {
                try {
                    double amount = indonesiaFormat.parse(depositAmount).doubleValue();
                    account.deposit(amount);
                    JOptionPane.showMessageDialog(mainFrame, "Deposit berhasil!");
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(mainFrame, "Format angka tidak valid!");
                }
            }
        }

        private void handleWithdraw() {
            String withdrawAmount = JOptionPane.showInputDialog(mainFrame,
                    "Masukkan jumlah penarikan (contoh: 25.000 atau 30.000,50):");
            if (withdrawAmount != null) {
                try {
                    double amount = indonesiaFormat.parse(withdrawAmount).doubleValue();
                    boolean success = account.withdraw(amount);
                    if (success) {
                        JOptionPane.showMessageDialog(mainFrame, "Penarikan berhasil!");
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "Saldo tidak mencukupi!");
                    }
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(mainFrame, "Format angka tidak valid!");
                }
            }
        }

        private void showBalance() {
            JOptionPane.showMessageDialog(mainFrame,
                    "Saldo saat ini: Rp" + indonesiaFormat.format(account.getBalance()));
        }

        private void showTransactionCount() {
            JOptionPane.showMessageDialog(mainFrame,
                    "Total Transaksi: " + BankAccount.getTransactionCount());
        }

        private void exitApplication() {
            JOptionPane.showMessageDialog(mainFrame,
                    "Terima kasih telah menggunakan ATM kami!");
            System.exit(0);
        }
    }
}