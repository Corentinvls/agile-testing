Feature: Fonctionnalités de la page model
	Scenario Outline: Vérification des différents caractétisque de la model 3
		Given je suis sur la page model
		When je suis sur l'onglet caractéristique
		And que je selectionne la "<gamme>"
		Then le poids doit etre "<weight>"
		And l'accéleration doit etre "0 à 100 km/h en <acceleration>"
		And l'autonomie doit etre "<autonomy>"
		Examples:
			| gamme                | weight   | acceleration | autonomy |
			| Performance          | 1,844 kg | 3,3 secondes | 567 km   |
			| Grande Automonie AWD | 1,844 kg | 4,4 secondes | 580 km   |
			| Standard Plus        | 1,745 kg | 5,6 secondes | 430 km   |