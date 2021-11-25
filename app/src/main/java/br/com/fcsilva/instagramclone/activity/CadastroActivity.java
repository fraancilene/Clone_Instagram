package br.com.fcsilva.instagramclone.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import br.com.fcsilva.instagramclone.R;
import br.com.fcsilva.instagramclone.helper.ConfiguraçãoFirebase;
import br.com.fcsilva.instagramclone.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    // criar variaveis para cada item da tela
    private EditText campoNome, campoEmail, campoSenha;
    private Button botaoCadastrar;
    private ProgressBar progressBar;

    private Usuario usuario;
    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        inicializaComponentes();

        // cadastrando usuario
        progressBar.setVisibility(View.GONE); //escondendo a progress bar
        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE); // exibindo a progresBar

                // recuperando o conteúdo de dentro dos campos
                String textoNome = campoNome.getText().toString();
                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();

                // verificando se os campos estão preenchidos
                if (!textoNome.isEmpty()){
                    if (!textoEmail.isEmpty()){
                        if (!textoSenha.isEmpty()){
                            usuario = new Usuario();
                            usuario.setNome(textoNome);
                            usuario.setEmail(textoEmail);
                            usuario.setSenha(textoSenha);
                            cadastrar(usuario);


                        } else {
                            Toast.makeText(CadastroActivity.this,
                                    "Preencha a senha", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        Toast.makeText(CadastroActivity.this,
                                "Preencha o email", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(CadastroActivity.this,
                            "Preencha o nome", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void cadastrar(Usuario usuario){
        // cadastrando um usuário
        autenticacao = ConfiguraçãoFirebase.getFirebaseAutemticação();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(
                this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(CadastroActivity.this,
                                    "Cadastro com sucesso", Toast.LENGTH_SHORT).show();

                        } else {
                            progressBar.setVisibility(View.GONE);

                            String erroExcecao = "";
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e){
                                erroExcecao = "Digite uma senha mais forte!";
                            } catch (FirebaseAuthInvalidCredentialsException e){
                                erroExcecao = "Digite um E-mail válido";
                            } catch(FirebaseAuthUserCollisionException e){
                                erroExcecao = "Esta conta já foi cadastrada";
                            }catch ( Exception e){
                                erroExcecao = "Ao cadastrar usuário: " + e.getMessage();
                                e.printStackTrace();
                            }

                            Toast.makeText(CadastroActivity.this,
                                    "Erro: " + erroExcecao, Toast.LENGTH_SHORT).show();
                        }

                    }
                }
        );


    }


    public void inicializaComponentes(){
        // inicializando todos os componentes
        campoNome = findViewById(R.id.editCadastroNome);
        campoEmail = findViewById(R.id.editCadastroEmail);
        campoSenha = findViewById(R.id.editCadastroSenha);
        botaoCadastrar = findViewById(R.id.buttonCadastrar);
        progressBar = findViewById(R.id.progressCadastro);
    }
}