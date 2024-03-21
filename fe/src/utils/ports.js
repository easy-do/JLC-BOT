


//四个连接点
export const allPorts = {
	groups: {
		top: {
			position: 'top',
			attrs: {
				circle: {
					r: 4,
					magnet: true,
					stroke: '#5F95FF',
					strokeWidth: 1,
					fill: '#fff',
					style: {
						visibility: 'hidden',
					},
				},
			},
		},
		right: {
			position: 'right',
			attrs: {
				circle: {
					r: 4,
					magnet: true,
					stroke: '#5F95FF',
					strokeWidth: 1,
					fill: '#fff',
					style: {
						visibility: 'hidden',
					},
				},
			},
		},
		bottom: {
			position: 'bottom',
			attrs: {
				circle: {
					r: 4,
					magnet: true,
					stroke: '#5F95FF',
					strokeWidth: 1,
					fill: '#fff',
					style: {
						visibility: 'hidden',
					},
				},
			},
		},
		left: {
			position: 'left',
			attrs: {
				circle: {
					r: 4,
					magnet: true,
					stroke: '#5F95FF',
					strokeWidth: 1,
					fill: '#fff',
					style: {
						visibility: 'hidden',
					},
				},
			},
		},
	},
	items: [
		{
			group: 'top',
		},
		{
			group: 'right',
		},
		{
			group: 'bottom',
		},
		{
			group: 'left',
		},
	],
};

//一个连接点
export const oneTopPorts = {
	groups: {
		top: {
			position: 'top',
			attrs: {
				circle: {
					r: 4,
					magnet: true,
					stroke: '#5F95FF',
					strokeWidth: 1,
					fill: '#fff',
					style: {
						visibility: 'hidden',
					},
				},
			},
		},
	},
	items: [
		{
			group: 'top',
		}
	],
};

//一个连接点
export const oneBottomPorts = {
	groups: {
		bottom: {
			position: 'bottom',
			attrs: {
				circle: {
					r: 4,
					magnet: true,
					stroke: '#5F95FF',
					strokeWidth: 1,
					fill: '#fff',
					style: {
						visibility: 'hidden',
					},
				},
			},
		},
	},
	items: [
		{
			group: 'bottom',
		}
	],
};

//两个连接点
export const topBottomPorts = {
	groups: {
		top: {
			position: 'top',
			attrs: {
				circle: {
					r: 4,
					magnet: true,
					stroke: '#5F95FF',
					strokeWidth: 1,
					fill: '#fff',
					style: {
						visibility: 'hidden',
					},
				},
			},
		},
		bottom: {
			position: 'bottom',
			attrs: {
				circle: {
					r: 4,
					magnet: true,
					stroke: '#5F95FF',
					strokeWidth: 1,
					fill: '#fff',
					style: {
						visibility: 'hidden',
					},
				},
			},
		},
	},
	items: [
		{
			group: 'top',
		},
		{
			group: 'bottom',
		},
	],
};

//是否的连接点
export const ifElsePorts = {
	groups: {
		top: {
			position: 'top',
			attrs: {
				circle: {
					r: 4,
					magnet: true,
					stroke: '#5F95FF',
					strokeWidth: 1,
					fill: '#fff',
					style: {
						visibility: 'hidden',
					},
				},
			},
		},
		right: {
			position: 'right',
			attrs: {
				circle: {
					r: 4,
					magnet: true,
					stroke: '#5F95FF',
					strokeWidth: 1,
					fill: '#fff',
					style: {
						visibility: 'hidden',
					},
				},
			},
		},
		left: {
			position: 'left',
			attrs: {
				circle: {
					r: 4,
					magnet: true,
					stroke: '#5F95FF',
					strokeWidth: 1,
					fill: '#fff',
					style: {
						visibility: 'hidden',
					},
				},
			},
		},
	},
	items: [
		{
			group: 'top',
		},
		{
			group: 'right',
		},
		{
			group: 'left',
		},
	],
};


export const portMap = {
	'allPorts' : allPorts,
	'topBottomPorts' : topBottomPorts,
	'ifElsePorts' : ifElsePorts,
	'oneTopPorts' : oneTopPorts,
	'oneBottomPorts' : oneBottomPorts,
}
