import React from "react";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
import "chartjs-adapter-luxon";
import ChartStreaming from "chartjs-plugin-streaming";
import { Line } from "react-chartjs-2";
import { faker } from "@faker-js/faker";
import zoomPlugin from "chartjs-plugin-zoom";
import moment from "moment";

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
  ChartStreaming,
  zoomPlugin
);

export const options = {
  elements: {
    line: {
      tension: 0.5,
    },
  },
  scales: {
    xAxes: {
      type: "realtime",
      distribution: "linear",
      realtime: {
        onRefresh: function (chart) {
          chart.data.datasets[0].data.push({
            x: Date.now(),
            y: Math.random() * 100,
          });
        },
        delay: 3000,
        time: {
          displayFormat: "h:mm",
        },
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
        callback: function (value) {
          return moment(value, "HH:mm:ss").format("mm:ss");
        },
      },
    },
    
    yAxes: {
      ticks: {
        beginAtZero: true,
        max: 1,
      },
    },
  },
};

const labels = [];
for (let time = 0; time <= 720; time++) {
  labels.push(time);
}

export const data = {
  datasets: [
    {
      label: "Dataset 1 (linear interpolation)",
      backgroundColor: "#fff",
      borderColor: "blue",
      fill: false,
      lineTension: 0,
      borderDash: [8, 4],
      data: labels.map(() => faker.datatype.number({ min: -200, max: 200 })),
    },
    {
      label: "Dataset 2 (linear interpolation)",
      backgroundColor: "#ececec",
      borderColor: "blue",
      fill: false,
      lineTension: 0,
      borderDash: [8, 4],
      data: labels.map(() => faker.datatype.number({ min: -200, max: 200 })),
    },
  ],
};

export function Chart() {
  return (
    <div style={{ maxWidth: 700, width: "100%" }}>
      <Line options={options} data={data} />
    </div>
  );
}
