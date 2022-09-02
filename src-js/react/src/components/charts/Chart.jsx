import React, { useState } from "react";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
  Filler,
} from "chart.js";
import "chartjs-adapter-luxon";
import zoomPlugin from "chartjs-plugin-zoom";
import ChartStreaming from "chartjs-plugin-streaming";
import { Line } from "react-chartjs-2";
import { faker } from "@faker-js/faker";
import moment from "moment";
import { useTranslation } from "react-i18next";

ChartJS.register(
  CategoryScale,
  zoomPlugin,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
  ChartStreaming,
  Filler
);

export function Chart({ leftS, stationNumber, voltage, power }) {
  const [voltageChart, setVoltageChart] = useState([]);
  const [powerChart, setPowerChart] = useState([]);
  const { t } = useTranslation();

  const labels = [];
  for (let time = 0; time <= 720; time++) {
    labels.push(time);
  }

  const chartStop = leftS === 0 ? true : false;

  const onRecieve = (chart) => {
    console.log(powerChart);
    console.log(voltageChart);
    setPowerChart((old) => [
      ...old,
      {
        x: Date.now(),
        y: power,
      },
    ]);
    setVoltageChart((old) => [
      ...old,
      {
        x: Date.now(),
        y: voltage,
      },
    ]);
    chart.update("quiet");
  };

  const data = {
    datasets: [
      {
        label: t("power"),
        backgroundColor: "rgba(237, 140, 140, 0.5)",
        fill: true,
        lineTension: 0,
        borderDash: [8, 4],
        borderColor: "rgb(237, 121, 121)",
        cubicInterpolationMode: "monotone",
        yAxisID: "power",
        data: powerChart,
      },
      {
        label: t("voltage"),
        backgroundColor: "rgba(208, 188, 245, 0.5)",
        fill: true,
        lineTension: 0,
        borderDash: [8, 4],
        borderColor: "rgb(169, 149, 207)",
        cubicInterpolationMode: "monotone",
        yAxisID: "voltage",
        data: voltageChart,
      },
    ],
  };

  const options = {
    responsive: true,
    interaction: {
      intersect: false,
    },
    plugins: {
      zoom: {
        pan: {
          enabled: true,
          mode: "x",
        },
        zoom: {
          pinch: {
            enabled: true,
          },
          wheel: {
            enabled: true,
          },
          mode: "x",
        },
        limits: {
          xy: {
            minDelay: null,
            maxDelay: null,
            minDuration: null,
            maxDuration: null,
          },
        },
      },
    },
    scales: {
      power: {
        type: "linear",
        display: true,
        position: "left",
        min: 0,
        max: 8,
      },
      voltage: {
        type: "linear",
        display: true,
        position: "right",
        min: 180,
        max: 260,

        // grid line settings
        grid: {
          drawOnChartArea: false, // only want the grid lines for one axis to show up
        },
      },
      xAxes: {
        type: "realtime",
        distribution: "linear",
        realtime: {
          duration: 20000,
          delay: 4000,
          pause: chartStop,
          onRefresh: onRecieve,
          time: {
            displayFormat: "h:mm",
          },
        },
        ticks: {
          callback: function (value) {
            return moment(value, "HH:mm:ss").format("mm:ss");
          },
        },
      },
    },
  };
  return (
    <div style={{ maxWidth: 700, width: "100%" }}>
      <Line options={options} data={data} />
    </div>
  );
}
