function DiagrammeVoie () {
	
	this.minTemps = 5;
	this.maxTemps = 27;

	this.sizeChar = 8;
	this.sizeGraduation = 4;
	this.spaceGraduation = 50;

	this.widthSVG = 914;
	this.widthOrdonnees = 100;
	this.widthGraph = 100;
	this.widthGraduation;
	
	this.heightSVG = 0;
	this.heightTitle = 30;
	this.heightText = 12;
	this.heightGraph = -this.spaceGraduation;
	this.heightAbscisses = 40;
	
	this.margin = 5;
	
	this.draw;
	
	if ( typeof DiagrammeVoie.initialized == "undefined" ) {
		
		DiagrammeVoie.prototype.initialiserPaper = function(document, dataJSON, indexVoie) {

			this.calculHeight(dataJSON, indexVoie);
			this.calculWidth(document);
			
			if(this.heightSVG > 0) {
				
				var domElement = document.createElement("div");
				domElement.setAttribute("id", "diagramme-" + indexVoie)
				document.getElementById("diagramme").appendChild(domElement);
				
				$.getScript("../resources/js/plugins/svg/svg.min.js");
				if (SVG.supported) {
					this.draw = SVG('diagramme-' + indexVoie).size(this.widthSVG, this.heightSVG);
					
					// Ajout du titre
					this.draw.text("Voie " + dataJSON[3][indexVoie].id)
						.x(this.margin)
						.y(0);
					
					// Ajout de l'axe des abscisses
					this.draw.line(
						this.margin + this.widthOrdonnees, 
						this.margin + this.heightTitle + this.heightGraph, 
						this.margin + this.widthOrdonnees + this.widthGraph, 
						this.margin + this.heightTitle + this.heightGraph)
						.stroke({ width: 1 });
					
					// Ajout des graduations de l'axe des abscisses
					var indexAbscisses;
					this.widthGraduation = this.widthGraph / (this.maxTemps - this.minTemps);
					for(indexAbscisses = 0; indexAbscisses < (this.maxTemps - this.minTemps + 1) ; ++indexAbscisses) {
						
						this.draw.line(
							this.margin + this.widthOrdonnees + indexAbscisses * this.widthGraduation, 
							this.margin + this.heightTitle + this.heightGraph - this.sizeGraduation/2, 
							this.margin + this.widthOrdonnees + indexAbscisses * this.widthGraduation, 
							this.margin + this.heightTitle + this.heightGraph + this.sizeGraduation/2)
							.stroke({ width: 1 });
						
						var heure = indexAbscisses + this.minTemps;
						this.draw.text(heure.toString())
							.x(this.margin + this.widthOrdonnees + indexAbscisses * this.widthGraduation - (heure.toString().length-1) * this.sizeChar)
							.y(this.margin + this.heightTitle + this.heightGraph + this.heightText)
							.font({
								size: this.heightText
							});
						
					}
					
					// Ajout de l'axe des ordonnées
					this.draw.line(
						this.margin + this.widthOrdonnees, 
						this.margin + this.heightTitle, 
						this.margin + this.widthOrdonnees, 
						this.margin + this.heightTitle + this.heightGraph)
						.stroke({ width: 1 });
					
					// Ajout des graduations de l'axe des ordonnées
		    		for(indexObject = 0; indexObject < dataJSON[1][indexVoie].length; ++indexObject) {
		    			
		    			this.draw.line(
	    					this.margin + this.widthOrdonnees - this.sizeGraduation/2, 
							this.margin + this.heightTitle + indexObject * this.spaceGraduation, 
	    					this.margin + this.widthOrdonnees + this.sizeGraduation/2, 
							this.margin + this.heightTitle + indexObject * this.spaceGraduation)
							.stroke({ width: 1 });

						this.draw.text(dataJSON[1][indexVoie][indexObject].nom)
							.x(this.margin)
							.y(this.margin + this.heightTitle + indexObject * this.spaceGraduation - this.heightText/2)
							.font({
								size: this.heightText
							});
						
		 	    		console.log(dataJSON[1][indexVoie][indexObject]);
		    		}
				} else {
					alert('SVG not supported');
				}
			
			}
			
		}

		DiagrammeVoie.prototype.calculHeight = function(dataJSON, indexVoie) {
			var length = dataJSON[1][indexVoie].length;
	   		if(length == 0) {
	   			this.heightSVG = 0;
	   		} else {
	   	   		for(indexObject = 0; indexObject < length; ++indexObject)
	   	   		{
	   	   			this.heightGraph+= this.spaceGraduation;
	   	   		}
   	   			this.heightSVG = 2 * this.margin + this.heightTitle + this.heightGraph +  this.heightAbscisses;
	   		}
		}
		
		DiagrammeVoie.prototype.calculWidth = function(document) {
			var element = document.getElementById("diagramme");
			this.widthSVG = element.offsetWidth - parseFloat(window.getComputedStyle(element).paddingLeft) - parseFloat(window.getComputedStyle(element).paddingRight);
			this.widthGraph = this.widthSVG - 2 * this.margin - this.widthOrdonnees;
		}

		DiagrammeVoie.prototype.paintActions = function(dataJSON, indexVoie) {
			
			if(dataJSON[0][indexVoie].length > 0) {
				var indexAction = 0;
				var vehiculeId = dataJSON[0][indexVoie][0].vehiculeId;
				var actionPrecedente = dataJSON[0][indexVoie][0];
				for(indexAction = 0; indexAction < dataJSON[0][indexVoie].length; ++indexAction) {
					var actionActuelle = dataJSON[0][indexVoie][indexAction];
					
					if(actionActuelle.vehiculeId == vehiculeId) {
						if(actionPrecedente.typeAction == 1 && actionActuelle.typeAction == 0) {
							this.draw.line(
								this.margin + this.widthOrdonnees + this.timeStringToFloat(actionPrecedente.time) * this.widthGraduation - this.minTemps, 
								this.margin + this.heightTitle + this.getIndexOfArret(dataJSON, indexVoie, actionPrecedente.parametre) * this.spaceGraduation, 
								this.margin + this.widthOrdonnees + this.timeStringToFloat(actionActuelle.time) * this.widthGraduation - this.minTemps, 
								this.margin + this.heightTitle + this.getIndexOfArret(dataJSON, indexVoie, actionActuelle.parametre) * this.spaceGraduation)
								.stroke({ color: '#f06', width: 1 });
							
							actionPrecedente = actionActuelle;
						} else if(actionPrecedente.typeAction == 0 && actionActuelle.typeAction == 1) {
							this.draw.line(
								this.margin + this.widthOrdonnees + this.timeStringToFloat(actionPrecedente.time) * this.widthGraduation - this.minTemps, 
								this.margin + this.heightTitle + this.getIndexOfArret(dataJSON, indexVoie, actionPrecedente.parametre) * this.spaceGraduation, 
								this.margin + this.widthOrdonnees + this.timeStringToFloat(actionActuelle.time) * this.widthGraduation - this.minTemps, 
								this.margin + this.heightTitle + this.getIndexOfArret(dataJSON, indexVoie, actionActuelle.parametre) * this.spaceGraduation)
								.stroke({ color: '#f06', width: 1 });
							
							actionPrecedente = actionActuelle;
						} else {
							vehiculeId = dataJSON[0][indexVoie][indexAction].vehiculeId;
							actionPrecedente = dataJSON[0][indexVoie][indexAction];
						}
					} else {
						vehiculeId = dataJSON[0][indexVoie][indexAction].vehiculeId;
						actionPrecedente = dataJSON[0][indexVoie][indexAction];
					}
				}
			}
			
		}
		
		DiagrammeVoie.prototype.getIndexOfArret = function(dataJSON, indexVoie, idArret) {
			
			var trouve = false;
			var indexArret = 0;
			while(!trouve && indexArret < dataJSON[1][indexVoie].length) {
				trouve = dataJSON[1][indexVoie][indexArret].id == idArret;
				indexArret++;
			}
			
			if(trouve) {
				return --indexArret;
			}
			
		}
		
		DiagrammeVoie.prototype.timeStringToFloat = function(time) {
			  var hoursMinutes = time.split(/[.:]/);
			  var hours = parseInt(hoursMinutes[0], 10);
			  var minutes = hoursMinutes[1] ? parseInt(hoursMinutes[1], 10) : 0;
			  
			  var value = hours + minutes / 60;
			  if(value < this.minTemps && 24 + value < this.maxTemps) {
				  value = 24 + value;
			  }
			  
			  return hours + minutes / 60;
			}
		
		DiagrammeVoie.initialized = true;
	}
}