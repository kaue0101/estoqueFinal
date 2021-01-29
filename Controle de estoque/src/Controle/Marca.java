package Controle;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controle.DAO;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Marca extends JFrame {

	/* Criando objeto jdbc */
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	private JPanel contentPane;
	private JTextField textproduto;
	private JTextField textpreco;
	private JTextField textmarca;
	private JTextField textprodutos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Marca frame = new Marca();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Marca() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Connection con = null;
		con = DAO.conectar();
		if (con != null) {
			System.out.println("Banco conectado");
		} else {
			System.out.println("Erro de conexão");
		}

		JButton btnprodutos = new JButton("");
		btnprodutos.setIcon(new ImageIcon(Marca.class.getResource("/icones/iconfinder_delet_4200550.png")));
		btnprodutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletar();

			}
		});
		btnprodutos.setToolTipText("excluir");
		btnprodutos.setBounds(319, 160, 90, 90);
		contentPane.add(btnprodutos);

		JButton btnquantidade = new JButton("");
		btnquantidade.setIcon(new ImageIcon(Marca.class.getResource("/icones/iconfinder_ic_add_circle_48px_3669476 (2).png")));
		btnquantidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnquantidade.setToolTipText("add");
		btnquantidade.setBounds(0, 160, 90, 90);
		contentPane.add(btnquantidade);

		JButton btnpreco = new JButton("");
		btnpreco.setIcon(new ImageIcon(Marca.class.getResource("/icones/iconfinder_icons_edit_1564503 (1).png")));
		btnpreco.setToolTipText("altarar");
		btnpreco.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterar();
			}
		});
		btnpreco.setBounds(166, 160, 90, 90);
		contentPane.add(btnpreco);

		textproduto = new JTextField();
		textproduto.setBounds(117, 72, 199, 20);
		contentPane.add(textproduto);
		textproduto.setColumns(10);

		textpreco = new JTextField();
		textpreco.setBounds(112, 113, 57, 20);
		contentPane.add(textpreco);
		textpreco.setColumns(10);

		textmarca = new JTextField();
		textmarca.setBounds(51, 10, 96, 20);
		contentPane.add(textmarca);
		textmarca.setColumns(10);

		textprodutos = new JTextField();
		textprodutos.setBounds(251, 113, 96, 20);
		contentPane.add(textprodutos);
		textprodutos.setColumns(10);

		JLabel lblNewLabel = new JLabel("Marca");
		lblNewLabel.setBounds(10, 11, 72, 19);
		contentPane.add(lblNewLabel);

		JButton btnPesquisar = new JButton("PESQUISAR");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisar();
			}
		});
		btnPesquisar.setBounds(166, 9, 89, 23);
		contentPane.add(btnPesquisar);

		JLabel label = new JLabel("");
		label.setBounds(24, 86, 48, 14);
		contentPane.add(label);

		JLabel lblProduto = new JLabel("PRODUTO");
		lblProduto.setBounds(40, 75, 76, 14);
		contentPane.add(lblProduto);

		JLabel lblQuantidade = new JLabel("QUANTIDADE");
		lblQuantidade.setBounds(10, 116, 90, 14);
		contentPane.add(lblQuantidade);

		JLabel lblPreco = new JLabel("PRECO");
		lblPreco.setBounds(201, 116, 48, 14);
		contentPane.add(lblPreco);

	}

	/** CRUD CREATE **/

	private void adicionar() {
		if (textpreco.getText().isEmpty() || textproduto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios");
		} else {

			String adicionar = "insert into itens(produtos,quantidade,preco) values(?,?,?)";
			try {
				con = DAO.conectar();
				pst = con.prepareStatement(adicionar);
				// a linha abaixo obtem o valor da caixa de texto txtnome e armazena(set) no
				// campo 1 do banco de dados
				pst.setString(1, textproduto.getText());
				pst.setString(2, textpreco.getText());
				pst.setString(3, textprodutos.getText());

				int adicionado = pst.executeUpdate();
				if (adicionado == 1) {
					JOptionPane.showMessageDialog(null, "produto adicionado");
				}
				// limpar campos
				limpar();

				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	/** CRUD READ **/

	private void pesquisar() {
		String create = "select * from itens where marca = ?";
		try {
			/* abrir a conexão com o bnco */
			con = DAO.conectar();
			// a linha abaixo irá executar a instrução
			pst = con.prepareStatement(create);
			// substituir o argumento ? pelo conteúdo da caixa de texto txtId (1 é o
			// primeiro campo da tabela)
			pst.setString(1, textmarca.getText());
			rs = pst.executeQuery();// recuperar os dados do banco
			// setar os campos do formulário com as informações recuperadas do banco
			// rs.next -> significa se houver informações para recuperar
			if (rs.next()) {
				textproduto.setText(rs.getString(2));
				textpreco.setText(rs.getString(3));
				textprodutos.setText(rs.getString(4));
			} else {
				// mensagem ao usuário
				JOptionPane.showMessageDialog(null, "bebeida não cadastrada");
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/** CRUD UPDATE **/

	private void alterar() {
		String update = "update itens set produtos=?,quantidade=?,preco=? where marca=?";
		try {
			con = DAO.conectar();
			pst = con.prepareStatement(update);
			pst.setString(1, textproduto.getText());
			pst.setString(2, textpreco.getText());
			pst.setString(3, textprodutos.getText());
			pst.setString(4, textmarca.getText());
			// confirmação de alteração
			int adicionado = pst.executeUpdate();
			if (adicionado == 1) {
				JOptionPane.showMessageDialog(null, "produto alterado com sucesso");
			} else {
				JOptionPane.showMessageDialog(null, "Não foi possível alterar o bebida");
			}
			con.close();
			// limpar os campos
			limpar();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/** CRUD DELETE **/

	private void deletar() {
		int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover esta bebida?", "Atenção",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String remover = "delete from itens where marca = ?";
			try {
				con = DAO.conectar();
				pst = con.prepareStatement(remover);
				pst.setString(1, textmarca.getText());
				int removido = pst.executeUpdate();
				if (removido == 1) {
					limpar();
					JOptionPane.showMessageDialog(null, "Veículo Removido com sucesso");

				} else {
					JOptionPane.showMessageDialog(null, "Não foi possível remover este veículo");
				}
				con.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);

			}
		}
	}

	/** Limpar campos do formulário **/
	private void limpar() {
		textproduto.setText(null);
		textpreco.setText(null);
		textprodutos.setText(null);
		textmarca.setText(null);
	}

}
