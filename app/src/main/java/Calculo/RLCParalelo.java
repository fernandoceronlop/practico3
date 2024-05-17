package Calculo;
import java.lang.Math;
import java.util.Arrays;
import java.util.List;
import android.util.Log;
public class RLCParalelo {

    public double calcAlpha(double resistencia, double capacitancia){
        double alpha =(1)/(2*resistencia*capacitancia);
        return  alpha;
    }
    public double calcOmega( double inductancia, double capacitancia){
        double omega = (1)/(Math.sqrt(inductancia*capacitancia));
        return omega;
    }
    //Corregido
    public static List<Double> ConstantesVoltSobreAmortiguado( double resistencia, double inductancia, double capacitancia, double corrienteI, double voltajeI ){
        double alpha = 1/(2*resistencia*capacitancia);
        double omega0= 1/Math.sqrt(inductancia*capacitancia);
        double s1 = -alpha + Math.sqrt(Math.pow(alpha,2)-Math.pow(omega0,2));
        double s2 = -alpha - Math.sqrt(Math.pow(alpha,2)-Math.pow(omega0,2));
        /*
        * A1+A2 = voltajeI ==> a1x + b1y = c1
        * s1A1 + s2A2 = Ir+Il/C ==> a2x + b2y = c2
        * */
        double A1 = 1, A2 = 1;
        double C1 = voltajeI;
        double C2 = ((voltajeI/resistencia)+(corrienteI))/(capacitancia);
        List<Double> solution = resolverEcuacion(A1,A2, C1, s1, s2, C2);
        if (solution == null) {
            return  null;
        } else {
            return  Arrays.asList(solution.get(0),solution.get(1),s1,s2);
        }
    }
    //No necesario modificar
    public List<Double> ConstantesCorrienteSobreAmortiguado(double resistencia, double inductancia, double capacitancia, double corrienteI, double voltajeI){
        double alpha = this.calcAlpha(resistencia,capacitancia);
        double omega0= this.calcOmega(inductancia,capacitancia);
        double s1 = -alpha + Math.sqrt(Math.pow(alpha,2)-Math.pow(omega0,2));
        double s2 = -alpha - Math.sqrt(Math.pow(alpha,2)-Math.pow(omega0,2));
        /*
         * A1+A2 = corrienteI ==> a1x + b1y = c1
         * s1A1 + s2A2 = voltajeI/inductancia ==> a2x + b2y = c2
         * */
        double A1 = 1, A2 = 1;
        double C1 = corrienteI;
        double C2 = voltajeI/inductancia;
        List<Double> solution = resolverEcuacion(A1,A2, C1, s1, s2, C2);
        if (solution == null) {
            return  null;
        } else {
            return  Arrays.asList(solution.get(0),solution.get(1),s1,s2);
        }
    }

    public double funcionTSobreAmortiguada (double a1, double a2, double s1, double s2, double t){
        double funcionFinal =  a1*Math.pow(Math.E,s1*t) + a2*Math.pow(Math.E,s2*t);
        return funcionFinal;
    }
    //No necesario modificar
    public static List<Double> ConstanteCorrienteCriticamente( double resistencia, double inductancia, double capacitancia, double corrienteI, double voltajeI ){
        double alpha = 1/(2*resistencia*capacitancia);
        double a2 = corrienteI;
        double a1 = (voltajeI/inductancia) +(alpha*a2) ;
        return Arrays.asList(a1,a2);
    }
    //Modificado
    public static List<Double> ConstanteVoltCriticamente( double resistencia, double inductancia, double capacitancia, double corrienteI, double voltajeI ){
        double alpha = 1/(2*resistencia*capacitancia);
        double a2 = voltajeI;
        double calculo1 = (voltajeI/resistencia)+corrienteI;
        double a1 = ((calculo1)/capacitancia) +(alpha*a2) ;
        return Arrays.asList(a1,a2);
    }
    public double funcionTCriticamenteAmortiguada (double a1, double a2, double alpha, double t){
        double funcionFinal = (a1*t +a2) *Math.pow(Math.E,-alpha * t);
        return funcionFinal;
    }
    //No necesario modificar
    public static List<Double> ConstanteCorrienteSub( double resistencia, double inductancia, double capacitancia, double corrienteI, double voltajeI ){
        double omega0= 1/Math.sqrt(inductancia*capacitancia);
        double alpha = 1/(2*resistencia*capacitancia);
        double omegad= Math.sqrt(Math.pow(omega0,2)- Math.pow(alpha,2));
        double a1 = corrienteI;
        double calculo1 = (voltajeI/inductancia)+(alpha*a1);
        double a2 = calculo1/omegad;
        return Arrays.asList(a1,a2,omegad);
    }
    //Modificado
    public static List<Double> ConstanteVoltSub( double resistencia, double inductancia, double capacitancia, double corrienteI, double voltajeI ){
        double omega0= 1/Math.sqrt(inductancia*capacitancia);
        double alpha = 1/(2*resistencia*capacitancia);
        double omegad= Math.sqrt(Math.pow(omega0,2)- Math.pow(alpha,2));
        double a1 = voltajeI;
        double calculo1 = ((voltajeI/resistencia)+(corrienteI)/capacitancia)+(alpha*a1);
        double a2 = calculo1/omegad;
        return Arrays.asList(a1,a2,omegad);
    }
    public double funcionTSubAmortiguada (double a1, double a2, double alpha,double omegad, double t){
        double funcionFinal = (a1*Math.cos(omegad*t) + a2*Math.sin(omegad*t)) * Math.pow(Math.E,-alpha*t)  ;
        return funcionFinal;
    }
    public static List<Double> resolverEcuacion(double a1, double b1, double c1, double a2, double b2, double c2) {
        double determinant = a1 * b2 - a2 * b1;
        if (determinant == 0) {
            return null; // No hay solución única
        } else {
            double x = (c1 * b2 - c2 * b1) / determinant;
            double y = (a1 * c2 - a2 * c1) / determinant;
            return Arrays.asList(x, y);
        }
    }
}
