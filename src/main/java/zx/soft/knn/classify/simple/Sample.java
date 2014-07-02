package zx.soft.knn.classify.simple;

import java.io.Serializable;

/**
 * This Sample class is a data structure that contains the class label
 * and its attribute.
 *  
 * @author wanggang
 *
 */
public class Sample implements Serializable {

	private static final long serialVersionUID = 562026058234594399L;

	/********** Variables *************/
	private int classLabel;
	private double[] attributes;

	/********** Methods ***************/

	/** Constructor
	* @param classLabel contains the class of this example
	* @param attributes contains the array of attributes to be stored
	**/
	public Sample(int classLabel, double[] attributes) {
		this.classLabel = classLabel;
		this.attributes = new double[attributes.length];
		for (int i = 0; i < attributes.length; i++) {
			this.attributes[i] = attributes[i];
		}
	}

	public Sample() {
		//
	}

	/** Accessor for the classLabel
	* @return classLabel
	**/
	public int getClassLabel() {
		return classLabel;
	}

	/** Accessor for a single attribue in the attribute array
	* @return attribute value in position index
	**/
	public double getAttribute(int index) {
		return attributes[index];
	}

	public double[] getAttributes() {
		return attributes;
	}

}