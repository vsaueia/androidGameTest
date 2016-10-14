package casadodocodigo.bis.controle;

import casadodocodigo.bis.objetos.Shoot;

/**
 * Created by viniciussilva on 06/10/2016.
 */

public interface ShootEngineDelegate {
    void createShoot(Shoot shoot);

    void removeShoot(Shoot shoot);
}
