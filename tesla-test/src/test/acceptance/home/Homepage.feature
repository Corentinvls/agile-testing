Feature: Fonctionnalités de ma page d'accueil
	Scenario: Vérification du titre
		Given je suis sur la page d'accueil
		Then le titre doit etre "Voitures électriques, énergie solaire et propre | Tesla France"

	Scenario: Vérification de la description
		Given je suis sur la page d'accueil
		Then la description doit etre "Tesla accélère la transition mondiale vers une énergie durable en proposant des véhicules électriques, des panneaux solaires et des solutions intégrées d'énergie renouvelable pour les particuliers et les entreprises."

	Scenario: Vérification des punchlines
		Given je suis sur la page d'accueil
		Then la page contient plusieurs punchlines qui doivent etre
			| Model 3 | Model S | Model X | Model Y | Systèmes d'énergie solaire et Powerwalls |

	Scenario Outline: Vérification du menu de navigation principal
		Given je suis sur la page d'accueil
		Then l'item <index> du menu doit etre "<name>" et redirige vers "<url>"
		Examples:
			| index | name      | url                                   |
			| 0     | Model S   | https://www.tesla.com/fr_fr/models    |
			| 1     | Model 3   | https://www.tesla.com/fr_fr/model3    |
			| 2     | Model X   | https://www.tesla.com/fr_fr/modelx    |
			| 3     | Model Y   | https://www.tesla.com/fr_fr/modely    |
			| 4     | Powerwall | https://www.tesla.com/fr_fr/powerwall |
			| 5     | Recharger | https://www.tesla.com/fr_fr/charging  |

	Scenario: Vérification du menu burger
		Given je suis sur la page d'accueil
		When je clique sur le menu burger
		Then le menu contient
			| Véhicules disponibles | Véhicules d'occasion | Reprise | Cybertruck | Roadster | Énergie | Essais | Flottes &amp; Entreprises | Nous trouver | Événements | Assistance |