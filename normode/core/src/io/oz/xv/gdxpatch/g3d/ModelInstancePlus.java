package io.oz.xv.gdxpatch.g3d;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

import net.mgsx.gltf.scene3d.model.NodePlus;

public class ModelInstancePlus extends ModelInstance {
	/** Constructs a new ModelInstance with only the specified nodes and materials of the given model. */
	public ModelInstancePlus (final Model model, final Matrix4 transform, final String... rootNodeIds) {
		super(model, transform, rootNodeIds);
		// this.model = model;
		// this.transform = transform == null ? new Matrix4() : transform;
		if (rootNodeIds == null)
			copyNodesPlus(model.nodes);
		else
			copyNodesPlus(model.nodes, rootNodeIds);
		copyAnimations(model.animations, defaultShareKeyframes);
		calculateTransforms();
	}

	/** Constructs a new ModelInstance with only the specified nodes and materials of the given model. */
	public ModelInstancePlus (final Model model, final Matrix4 transform, final Array<String> rootNodeIds, boolean shareKeyframes) {
		super(model, transform, rootNodeIds, shareKeyframes);
		// this.modelPlus = model;
		// this.transform = transform == null ? new Matrix4() : transform;
		copyNodesPlus(model.nodes, rootNodeIds);
		copyAnimations(model.animations, shareKeyframes);
		calculateTransforms();
	}
	
	/** Constructs a new ModelInstance which is an copy of the specified ModelInstance. */
	public ModelInstancePlus (ModelInstancePlus copyFrom, final Matrix4 transform, boolean shareKeyframes) {
		super(copyFrom, transform, shareKeyframes);
		// this.model = copyFrom.model;
		// this.transform = transform == null ? new Matrix4() : transform;
		copyNodesPlus(copyFrom.nodes);
		copyAnimations(copyFrom.animations, shareKeyframes);
		calculateTransforms();
	}

	protected void copyNodesPlus (Array<Node> nodes) {
		for (int i = 0, n = nodes.size; i < n; ++i) {
			final Node node = nodes.get(i);
			// this.nodes.add(node.copy());
			this.nodes.add(NodePlus.clone(node));
		}
		invalidatePlus();
	}

	protected void copyNodesPlus (Array<Node> nodes, final String... nodeIds) {
		for (int i = 0, n = nodes.size; i < n; ++i) {
			final Node node = nodes.get(i);
			for (final String nodeId : nodeIds) {
				if (nodeId.equals(node.id)) {
					// this.nodes.add(node.copy());
					this.nodes.add(NodePlus.clone(node));
					break;
				}
			}
		}
		invalidatePlus();
	}

	protected void copyNodesPlus (Array<Node> nodes, final Array<String> nodeIds) {
		for (int i = 0, n = nodes.size; i < n; ++i) {
			final Node node = nodes.get(i);
			for (final String nodeId : nodeIds) {
				if (nodeId.equals(node.id)) {
					// this.nodes.add(node.copy());
					this.nodes.add(NodePlus.clone(node));
					break;
				}
			}
		}
		invalidatePlus();
	}

	/** Makes sure that each {@link NodePart} of the {@link Node} and its sub-nodes, doesn't reference a node outside this node
	 * tree and that all materials are listed in the {@link #materials} array. */
	protected void invalidatePlus (Node node) {
		for (int i = 0, n = node.parts.size; i < n; ++i) {
			NodePart part = node.parts.get(i);
			ArrayMap<Node, Matrix4> bindPose = part.invBoneBindTransforms;
			if (bindPose != null) {
				for (int j = 0; j < bindPose.size; ++j) {
					bindPose.keys[j] = getNode(bindPose.keys[j].id);
				}
			}
			if (!materials.contains(part.material, true)) {
				final int midx = materials.indexOf(part.material, false);
				if (midx < 0)
					materials.add(part.material = part.material.copy());
				else
					part.material = materials.get(midx);
			}
		}
		for (int i = 0, n = node.getChildCount(); i < n; ++i) {
			invalidatePlus(node.getChild(i));
		}
	}

	/** Makes sure that each {@link NodePart} of each {@link Node} doesn't reference a node outside this node tree and that all
	 * materials are listed in the {@link #materials} array. */
	protected void invalidatePlus () {
		for (int i = 0, n = nodes.size; i < n; ++i) {
			invalidatePlus(nodes.get(i));
		}
	}


}
