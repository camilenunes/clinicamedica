package com.aati.scm.view;

import java.util.List;
import java.util.Scanner;

import com.aati.scm.model.dao.MedicoDAO;
import com.aati.scm.model.entity.Medico;


public class CadastroMedico {
    private final MedicoDAO dao = new MedicoDAO();
    private final Scanner sc = new Scanner(System.in);

    public void iniciar() {
        while (true) {
            System.out.println("\n=== Cadastro de Médicos ===");
            System.out.println("1 - Cadastrar médico");
            System.out.println("2 - Listar médicos");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            int opcao = sc.nextInt();
            sc.nextLine(); // limpar buffer

            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> listar();
                case 0 -> { System.out.println("Saindo..."); return; }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void cadastrar() {
        System.out.println("\n--- Novo Médico ---");
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("CRM: ");
        String crm = sc.nextLine();
        System.out.print("Especialidade: ");
        String especialidade = sc.nextLine();
        System.out.print("Telefone: ");
        String telefone = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Endereço: ");
        String endereco = sc.nextLine();
        System.out.print("Observações: ");
        String observacoes = sc.nextLine();

        Medico m = new Medico(0, nome, crm, especialidade, telefone, email, endereco, observacoes);

        if (dao.inserir(m)) {
            System.out.println("✅ Médico cadastrado com sucesso!");
        } else {
            System.out.println("❌ Erro ao cadastrar médico!");
        }
    }

    private void listar() {
        List<Medico> medicos = dao.listar();
        System.out.println("\n--- Lista de Médicos ---");
        for (Medico m : medicos) {
            System.out.println("ID: " + m.getId() + " | Nome: " + m.getNome() + " | CRM: " + m.getCrm());
        }
    }
}
