import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;


public class Faca extends Arma {
	
	
	private int estado;
	private boolean soltouTiro;
	private double oldAng;
	private double alcanceAtaque=50;
	private boolean atacou;

	public Faca() {
		atirou=false;
		setDano(Constantes.FACA_dano);
		setPeso(Constantes.FACA_peso);
		setRound(Constantes.FACA_round);
		setTempoEntreTiros(0);
		setTempoRecarrega(0);
		setValor(Constantes.FACA_valor);
		setSizeX(50);
		setSizeY(5);
		
		
	}
 
	@Override
	public void definePosicaoArma(double ang,double startX,double startY) {
		// TODO Auto-generated method stub
		
		setAngulo(ang);
		oldAng=ang;
		setX(startX);
		setY(startY);

	}

	@Override
	public void DesenhaSe(Graphics2D dbg, int XMundo, int YMundo) {
		// TODO Auto-generated method stub
			dbg.setColor(Color.black);
			AffineTransform trans = dbg.getTransform();
			dbg.translate(getX()-XMundo, getY()-YMundo);
			if(estado==1)
				dbg.rotate(oldAng);
			else 
				dbg.rotate(getAngulo());
			
			dbg.drawLine(0, 0, (int)alcanceAtaque/2, 0);

			dbg.setTransform(trans);
			
			if (estado==1) {
//				dbg.setColor(Color.LIGHT_GRAY);
//				dbg.fillRect(GamePanel.PWIDTH/2-50, GamePanel.PHEIGHT/2-205,(int)(tempoRecarrega*100/Constantes.PISTOLA_tempoRecarrega) , 20);

				dbg.setColor(Color.black);
				dbg.drawOval((int)(getX()-alcanceAtaque/2), (int)(getY()-alcanceAtaque/2), (int)alcanceAtaque,(int)alcanceAtaque);
				
			}
//			
//			dbg.drawString("Round: "+round,5 , 20);
//			dbg.drawString("mag: "+mag,5 , 30);
			
		
		
	}

	@Override
	public void SimulaSe(int Difftime) {
		// TODO Auto-generated method stub
		
		
		setTempoEntreTiros(getTempoEntreTiros() + Difftime);
		setTempoRecarrega(getTempoRecarrega() + Difftime);
		

			
		if (estado==0) {
			oldAng=getAngulo();
		
			if (getTempoEntreTiros()>=Constantes.FACA_tempoEntreTiros) {
				
				if (atirou&&soltouTiro) {	
					soltouTiro=false;
					atira();
					setTempoEntreTiros(0);
					atacou=false;
					
				}
				
				if (!atirou)
					soltouTiro=true;
			}
			
		}
		
		if (estado==1) {
			
			
			oldAng+=Math.PI*Difftime/Constantes.FACA_tempoAtaque;
				
				for (int i = 0; i < CanvasGame.inimigos.size(); i++) {
					if (Constantes.colidecircular(getX(), getY(), alcanceAtaque, CanvasGame.inimigos.get(i).X, CanvasGame.inimigos.get(i).Y, CanvasGame.inimigos.get(i).sizeX/2)) {
						CanvasGame.inimigos.get(i).life-=getDano();
						
					}
		
				}
				
			if (getTempoRecarrega()>=Constantes.FACA_tempoAtaque) {
				setTempoRecarrega(0);
				estado=0;	
			}
				
			
			
		}
//		if (estado==2) {
//			
//			if (round>0) 
//				estado=0;
//			
//			
//			}
	
		
	}
	private void atira() {
		// TODO Auto-generated method stub
		setAngulo(getAngulo() - (Math.PI/2));
		estado=1;
	}

	@Override
	public void recarrega() {
		// TODO Auto-generated method stub

	}

	@Override
	public void atirou() {
		// TODO Auto-generated method stub

			atirou=true;
			
		
	}



	@Override
	public void naoAtirou() {
		// TODO Auto-generated method stub
		atirou=false;

	}

}