$.getScript("../snapsvg/snap.svg-min.js", function(){
   alert("Script loaded and executed.");
});

function DiagrammeVoie () {
	this.heightSVG = 0;
	this.widthSVG = 914;
	this.heightAbscisses = 40;
	this.widthOrdonnees = 100;
	this.heightTitle = 30;
	this.sizeGraduation = 4;
	this.spaceGraduation = 50;
	this.margin = 5;
	this.paper;
	
	if ( typeof DiagrammeVoie.initialized == "undefined" ) {
		
		DiagrammeVoie.prototype.initialiserPaper = function(document, dataJSON, indexVoie) {

			this.calculHeight(dataJSON, indexVoie);
			this.calculWidth(document);
			// Initialisation du fond svg
			if(this.heightSVG > 0) {

				var textnode = document.createTextNode("<svg id='diagramme-" + indexVoie + "'></svg>");
				document.getElementById("diagramme").appendChild(textnode);

				this.paper = Snap("#diagramme-" + indexVoie + "");
				this.paper.attr({
				    width: LARGEUR_SVG,
				    height: HEIGHT_SVG
				});
				
				// Ajout du titre
				paper.text(this.margin, this.heightTitle, "Voie " + indexVoie);
				
				// Ajout de l'axe des abscisses
				var axeAbscisses = paper.line(
						this.margin + this.widthOrdonnees + this.sizeGraduation/2, 
						this.heightSVG - this.margin - this.heightTitle - this.sizeGraduation/2, 
						this.widthSVG - this.margin - this.heightAbscisses, 
						this.heightSVG - this.margin - this.heightAbscisses - this.sizeGraduation/2);
				axeAbscisses.attr({
				    fill: "#bada55",
				    stroke: "#000",
				    strokeWidth: 1
				});
				
				// Ajout des graduations de l'axe des abscisses
				var indexAbscisses;
				var deltaX = (LARGEUR_SVG - 2 * MARGIN) / (MAX_TEMPS - MIN_TEMPS);
				for(indexAbscisses = 0; indexAbscisses < (MAX_TEMPS - MIN_TEMPS) ; ++indexAbscisses) {
					var graduationsAbscisses = paper.line(MARGIN + indexAbscisses * deltaX, HEIGHT_SVG - MARGIN - HEIGHT_TEXTE + 2, MARGIN + indexAbscisses * deltaX, HEIGHT_SVG - MARGIN - HEIGHT_TEXTE - 2);
					graduationsAbscisses.attr({
	    			    fill: "#bada55",
	    			    stroke: "#000",
	    			    strokeWidth: 1
	    			});
				}
				
				// Ajout de l'axe des ordonnées
				var axeOrdonnees = paper.line(MARGIN, MARGIN + HEIGHT_TEXTE, MARGIN, HEIGHT_SVG - MARGIN - HEIGHT_TEXTE);
				axeOrdonnees.attr({
				    fill: "#bada55",
				    stroke: "#000",
				    strokeWidth: 1
				});
				
				// Ajout des graduations de l'axe des ordonnées
	    		for(indexObject = 0; indexObject < dataJSON[1][indexVoie].length; ++indexObject)
	    		{
	    			var graduationsOrdonnees = paper.line(MARGIN - 2, MARGIN + (indexObject + 1) * INTERVALLE_GRADUATION_ORDONNEES, MARGIN + 2, MARGIN + (indexObject + 1) * INTERVALLE_GRADUATION_ORDONNEES);
	    			graduationsOrdonnees.attr({
	    			    fill: "#bada55",
	    			    stroke: "#000",
	    			    strokeWidth: 1
	    			});
	    			
	 	    		console.log(dataJSON[1][indexVoie][indexObject]);
	    		}
			
			}
			
		}

		DiagrammeVoie.prototype.calculHeight = function(dataJSON, indexVoie) {
			var length = dataJSON[1][indexVoie].length;
	   		if(length == 0) {
	   			this.heightSVG = 0;
	   		} else {
	   			var heightSVG = 3 * this.margin + this.heightTitle + this.sizeGraduation + this.heightAbscisses;
	   	   		for(indexObject = 0; indexObject < length; ++indexObject)
	   	   		{
	   	   			this.heightSVG+= this.spaceGraduation;
	   	   		}
	   		}
		}
		
		DiagrammeVoie.prototype.calculWidth = function(document) {
			this.widthSVG = document.getElementById("diagramme").offsetWidth;
		}

		DiagrammeVoie.prototype.paintActions = function(dataJSON, indexVoie) {

			var indexAction;
			var vehiculeId = dataJSON[0][indexVoie][0].vehiculeId;
			var actionPrecedente = dataJSON[0][indexVoie][0];
			for(indexAction = 0; indexAction < dataJSON[0][indexVoie].length; ++indexAction) {
				var actionActuelle = dataJSON[0][indexVoie][indexAction];
				
				if(actionActuelle.vehiculeId == vehiculeId) {
					if(actionPrecedente.typeAction == 1 && actionActuelle.typeAction == 0) {
						console.log("Transition repérée");
						actionPrecedente = actionActuelle;
					}
					if(actionPrecedente.typeAction == 0 && actionActuelle.typeAction == 1) {
						console.log("Attente repérée");
						actionPrecedente = actionActuelle;
					}
				} else {
					vehiculeId = dataJSON[0][indexVoie][indexAction].vehiculeId;
					actionPrecedente = dataJSON[0][indexVoie][indexAction];
				}
			}
			
		}
		
		DiagrammeVoie.initialized = true;
	}
}