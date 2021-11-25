package br.com.fcsilva.instagramclone.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguraçãoFirebase {

    //classe de recuperação o objeto de autenticação do firebase

    private static DatabaseReference referenciaFirebase;
    private static FirebaseAuth referenciaAutenticação;

    // retornando a instância do FirebaseAuth
    public static FirebaseAuth getFirebaseAutemticação(){
        if (referenciaAutenticação == null){
            referenciaAutenticação = FirebaseAuth.getInstance();
        }
        return referenciaAutenticação;
    }

    // retorna a referencia do banco de dados
    public static DatabaseReference getFirebase(){
        if (referenciaFirebase == null) {
            referenciaFirebase = FirebaseDatabase.getInstance().getReference();
        }
        return  referenciaFirebase;
    }

}
