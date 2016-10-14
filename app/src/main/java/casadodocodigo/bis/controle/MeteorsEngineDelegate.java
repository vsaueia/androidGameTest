package casadodocodigo.bis.controle;

import casadodocodigo.bis.objetos.Meteor;

/**
 * Created by viniciussilva on 06/10/2016.
 */

public interface MeteorsEngineDelegate {
    void createMeteor(Meteor meteor, float x, float y, float vel, double ang, int vl);

    void createMeteor(Meteor meteor);

    void removeMeteor(Meteor meteor);
}
