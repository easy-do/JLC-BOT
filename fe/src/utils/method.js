import { message } from 'antd';
import dagre from 'dagre';

let count = 0;

export const removeNode = (graph) => {
	const cells = graph.getSelectedCells();
	if (cells.length) {
		const cellsId = cells[0]?.id;
		const allChildrenNode = [];
		const nodes = cells.filter((cell) => cell.isNode());
		nodes.forEach((node) => {
			//  获取后继单元格
			const successors = graph.getSuccessors(node, {
				depth: 3,
			});
			allChildrenNode.push(...successors); // 如果删除的是源节点 则删除后面所有节点
			if (allChildrenNode) {
				graph.removeCells(allChildrenNode);
			}
		});
		// 获取后继节点id
		let keyArr = [];
		allChildrenNode.forEach((i) => {
			keyArr.push(i?.id);
		});
		graph.removeCells(cells);
	}
}

export const startDragToGraph = (graph, type) => {
	let node = null;
	node = graph.addNode({
		shape: 'custom-react-node',
		label: type,
		x: 30,
		y: -30,
		id: `task${++count}`, // 设置节点id
	});
	message.success(`添加任务节点${node.id}成功！`);
	return node;
};

// 控制连接桩显示/隐藏
export function showPorts(ports, show) {
	for (let i = 0, len = ports.length; i < len; i = i + 1) {
		ports[i].style.visibility = show ? 'visible' : 'hidden';
	}
}

// 重置所有节点
export function reset(graph) {
	const nodes = graph.getNodes();
	const edges = graph.getEdges();

	nodes.forEach((node) => {
		node.attr('body/stroke', 'black');
	});
	edges.forEach((edge) => {
		edge.attr('line/stroke', 'black');
	});
}

// 重置表单
export const resetForm = (formEl) => {
	if (!formEl) return;
	formEl.resetFields();
};

/**
 * 自动布局
 * @param {*} g  画布
 */
export const autoLayout = (g) => {
	const layout = new dagre.graphlib.Graph();
	layout.setGraph({ rankdir: 'LR', ranksep: 50, nodesep: 50, controlPoints: true });
	layout.setDefaultEdgeLabel(() => ({}));

	g?.getNodes()?.forEach((node) => {
		layout.setNode(node.id, { width: node.size().width, height: node.size().height });
	});

	g?.getEdges()?.forEach((edge) => {
		layout.setEdge(edge.getSourceCell()?.id, edge.getTargetCell()?.id);
	});

	dagre.layout(layout);

	g?.getNodes()?.forEach((node) => {
		const pos = layout.node(node.id);
		node.position(pos.x, pos.y);
	});
	g?.centerContent();
};
