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
import { useTranslation } from "react-i18next";

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

export function Chart({ leftS, stationNumber, voltage, power }) {
  const { t } = useTranslation();
  const labels = [];
  for (let time = 0; time <= 720; time++) {
    labels.push(time);
  }

  const onRecieve = (chart) => {
    chart.data.datasets[0].data.push({
      x: Date.now(),
      y: power,
    });
    chart.data.datasets[1].data.push({
      x: Date.now(),
      y: voltage,
    });
    chart.update("quiet");
  };

  const data = {
    datasets: [
      {
        label: t("power"),
        backgroundColor: "#fff",
        borderColor: "red",
        fill: false,
        lineTension: 0,
        borderDash: [8, 4],
        yAxisID: "power",
        data: [],
      },
      {
        label: t("voltage"),
        backgroundColor: "#ececec",
        borderColor: "blue",
        fill: false,
        lineTension: 0,
        borderDash: [8, 4],
        yAxisID: "voltage",
        data: [],
      },
    ],
  };

  const options = {
    scales: {
      power: {
        type: "linear",
        display: true,
        position: "left",
        min: -1,
        max: 5,
      },
      voltage: {
        type: "linear",
        display: true,
        position: "right",
        min: 160,
        max: 280,

        // grid line settings
        grid: {
          drawOnChartArea: false, // only want the grid lines for one axis to show up
        },
      },
      xAxes: {
        type: "realtime",
        distribution: "linear",
        realtime: {
          delay: 2000,
          refresh: 2000,
          onRefresh: onRecieve,
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
    },
    interaction: {
      intersect: false,
    },
  };
  return (
    <div style={{ maxWidth: 700, width: "100%" }}>
      <Line options={options} data={data} />
    </div>
  );
}
