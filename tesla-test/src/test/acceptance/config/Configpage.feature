Feature: Fonctionnalités de la page configurateur Tesla Model S
	Scenario: Vérification du prix LOA de la Model S
		Given je suis sur la page configurateur
		When je clique sur le bouton commander
		Then l'url de la page doit être "https://www.tesla.com/fr_fr/models/design#battery"
		And le prix affiché est un prix "LOA" avec "A partir de 768 € /mois*"

	Scenario Outline: Vérification du prix LOA du Grande Autonomie Plus ou Performance
		Given je suis sur la page configurateur
		When je clique sur le bouton commander
		And je sélectionne le modèle "<model>"
		Then le prix affiché est un prix "LOA" avec "A partir de <LOA> /mois*"
		And une économies de carburant estimées avec "A partir de - <fuel> /mois"
		And un montant total avec achat au terme du contrat de "<total>"
	Examples:
		| model 				| LOA   | fuel 	   | total     |
		| Grande Autonomie Plus | 768 € | 108 €	   | 94 841 €  |
		| Performance 			| 969 € | 108 €	   | 114 052 € |

	Scenario: Vérification augmentation du prix LOA pour la conduite autonome
		Given je suis sur la page configurateur
		When je clique sur le bouton commander
		And je sélectionne le modèle "Performance"
		And je veux la capacité de conduite entièrement autonome
		Then le prix augmente de 89 €/mois

	Scenario: Localisation des points de vente
		Given je suis sur la page configurateur
		When je clique sur le bouton commander
		And je sélectionne le modèle "Performance"
		And je navique et click sur le lien Localisation
		Then l'url de la page doit être "https://www.tesla.com/fr_FR/findus/list"
