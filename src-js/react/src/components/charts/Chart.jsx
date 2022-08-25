import React from "react";
const Chart = require("react-chartjs-2").Chart;
import "chartjs-adapter-luxon";
import ChartStreaming from "chartjs-plugin-streaming";
import { Line } from "react-chartjs-2";
import { faker } from "@faker-js/faker";
import zoomPlugin from "chartjs-plugin-zoom";
import moment from "moment";

// ChartJS.register(
//   CategoryScale,
//   LinearScale,
//   PointElement,
//   LineElement,
//   Title,
//   Tooltip,
//   Legend,
//   ChartStreaming,
//   zoomPlugin
// );

export const options = {
  elements: {
    line: {
      tension: 0.5
    }
  },
  scale: {
    xAxes: [
      {
        type: "realtime",
        distribution: "linear",
        realtime: {
          onRefresh: function(chart) {
            chart.data.datasets[0].data.push({
              x: moment(),
              y: Math.random()
            });
          },
          delay: 3000,
          time: {
            displayFormat: "h:mm"
          }
        },
        ticks: {
          displayFormats: 1,
          maxRotation: 0,
          minRotation: 0,
          stepSize: 1,
          maxTicksLimit: 30,
          minUnit: "second",
          source: "auto",
          autoSkip: true,
          callback: function(value) {
            return moment(value, "HH:mm:ss").format("mm:ss");
          }
        }
      }
    ],
    yAxes: [
      {
        ticks: {
          beginAtZero: true,
          max: 1
        }
      }
    ]
  }
};


export const data = {
  datasets: [
    {
      label: "Dataset 1 (linear interpolation)",
      backgroundColor: "#fff",
      borderColor: 'red',
      fill: false,
      lineTension: 0,
      borderDash: [8, 4],
      data: []
    }
  ]
};

export function Charts() {
  return (
    <div style={{ maxWidth: 700, width: "100%" }}>
      <Line options={options} data={data} />
    </div>
  );
}
