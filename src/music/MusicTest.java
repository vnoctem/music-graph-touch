package music;


import music.instruments.Guitar;
import music.instruments.MusicInstrument;
import music.instruments.Piano;
import music.instruments.Violin;

// temporaire, pour tester les instruments
public class MusicTest {

	public static void main(String[] args) {
		MusicSynthesizer ms;
		
		try {
			// initialiser le synthétiseur et l'ouvrir
			ms = new MusicSynthesizer();
			ms.open();
			
			// créer un piano et l'assigner au MIDI channel 0
			MusicInstrument piano = new Piano();
			ms.setInstrument(piano, 0);
			
			// jouer le piano
//			new Thread(() -> {
//				for (int i = 30; i < 100; i++) {
//					
//					
//						ms.playNote(60, piano.getVelocity(), 0, piano.getDuration());
//						System.out.println("piano" + i);
//					
//				}
//			}).start();
			
			new Thread(new Runnable() {
				public void run() {
					for (int i = 30; i < 100; i++) {
						
						try {
							ms.playNote(i, piano.getVelocity(), 0, piano.getDuration());
							System.out.println("piano" + i);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
			
			
			
			// créer une guitare acoustique et l'assigner au MIDI channel 1
			MusicInstrument guitar = new Guitar();
			ms.setInstrument(guitar, 1);
			
			// jouer la guitare
//			new Thread(() -> {
//				for (int i = 100; i < 30; i--) {
//					try {
//						ms.playNote(i, guitar.getVelocity(), 1, guitar.getDuration());
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}).start();
			
			// créer un violon et l'assigner au MIDI channel 2
			MusicInstrument violin = new Violin();
			ms.setInstrument(violin, 2);
			Thread t = new Thread(new Runnable() {
				public void run() {
					//ms.playNote(60, violin.getVelocity(), 2, violin.getDuration());
				}
				
			});
			t.start();
			for (int i = 30; i < 100; i++) {
				ms.playNote(i, violin.getVelocity(), 2, violin.getDuration());
			}
			
			
			
			ms.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
