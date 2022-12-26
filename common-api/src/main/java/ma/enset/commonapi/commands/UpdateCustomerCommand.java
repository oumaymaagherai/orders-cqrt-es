package ma.enset.commonapi.commands;

import lombok.Getter;

public class UpdateCustomerCommand extends BaseCommand<String>{
    @Getter
    private String nom ;
    @Getter private String adresse ;
    @Getter private  String email  ;
    @Getter private String telephone ;

    public UpdateCustomerCommand(String id, String nom, String adresse,
                                 String email, String telephone) {
        super(id);
        this.nom = nom;
        this.adresse = adresse;
        this.email = email;
        this.telephone = telephone;
    }
}
