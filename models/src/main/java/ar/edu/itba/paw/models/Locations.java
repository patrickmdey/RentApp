package ar.edu.itba.paw.models;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;


public enum Locations {
    RETIRO("Retiro"), SAN_NICOLAS("San Nicolas"), PUERTO_MADERO("Puerto Madero"),
    SAN_TELMO("San Telmo"), MONTSERRAT("Montserrat"), CONSTITUCION("Constitucion"),
    RECOLETA("Recoleta"), BALVANERA("Balvanera"), SAN_CRISTOBAL("San Cristobal"),
    LA_BOCA("La Boca"), BARRACAS("Barracas"), PARQUE_PATRICOS("Parque Patricios"),
    NUEVA_POMPEYA("Nueva Pompeya"), ALMAGRO("Almagro"), BOEDO("Boedo"),
    CABALLITO("Caballito"), FLORES("Flores"), PARQUE_CHACABUCO("Parque Chacabuco"),
    VILLA_SOLDATI("Villa Soldati"), VILLA_RIACHUELO("Villa Riachuelo"),
    VILLA_LUGANO("Villa Lugano"), LINIERS("Liniers"),
    MATADEROS("Mataderos"), PARQUE_AVELLANEDA("Parque Avellaneda"),
    VILLA_REAL("Villa Real"), MONTE_CASTRO("Monte Castro"), VERSALLES("Versalles"),
    FLORESTA("Floresta"), VELEZ_SARSFIELD("Velez Sarfield"), VILLA_LURO("Villa Luro"),
    VILLA_GENERAL_MITRE("Villa General Mitre"), VILLA_DEVOTO("Villa Devoto"),
    VILLA_DEL_PARQUE("Villa del Parque"), VILLA_SANTA_RITA("Villa Santa Rita"),
    COGHLAN("Coghlan"), SAAVEDRA("Saavedra"), VILLA_URQUIZA("Villa Urquiza"),
    VILLA_PUEYRREDON("Villa Pueyrredon"),
    NUNEZ("Nuñez"), BELGRANO("Belgrano"), COLEGIALES("Colegiales"),
    PALERMO("Palermo"), CHACARITA("Chacarita"), VILLA_CRESPO("Villa Crespo"),
    LA_PATERNAL("La Paternal"), VILLA_ORTUZAR("Villa Ortuzar"),
    AGRONOMIA("Agronomia"), PARQUE_CHAS("Parque Chas");

    private final String name;

    Locations(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
