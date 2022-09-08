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
import ZoomPlugin from "chartjs-plugin-zoom";
import ChartStreaming from "chartjs-plugin-streaming";
import { Line } from "react-chartjs-2";
import moment from "moment";
import { useTranslation } from "react-i18next";

ChartJS.register(
  CategoryScale,
  ZoomPlugin,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
  ChartStreaming,
  Filler
);

export function Chart({ leftS, power }) {
  const [powerChart, setPowerChart] = useState([]);
  const [chartTap, setChartTap] = useState(false);
  const [chartFinish, setChartFinish] = useState(false);
  const { t } = useTranslation();

  const labels = [];
  for (let time = 0; time <= 720; time++) {
    labels.push(time);
  }

  const chartTimerFinish = () => {
    leftS === 0 ? setChartFinish(true) : setChartFinish(false);
  };
  const chartPauseTapping = () => {
    setChartTap(!chartTap);
  };
  const onRecieve = () => {
    console.log(powerChart);
    chartTimerFinish();
    setPowerChart((old) => [
      ...old,
      {
        x: Date.now(),
        y: Math.round(power),
      },
    ]);
  };

  const data = {
    datasets: [
      {
        label: t("power"),
        backgroundColor: "rgba(208, 188, 245, 0.5)",
        fill: false,
        lineTension: 0,
        borderDash: [8, 4],
        borderColor: "rgb(169, 149, 207)",
        cubicInterpolationMode: "monotone",
        xAxisID: "x",
        data: powerChart,
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
          rangeMax: {
            x: 4000,
          },
          rangeMin: {
            x: 0,
          },
        },
      },
    },
    scales: {
      y: {
        type: "linear",
        display: true,
        min: 0,
        max: 8,
      },
      x: {
        type: "realtime",
        distribution: "linear",
        realtime: {
          duration: 20000,
          // delay: 2000,
          refresh: 2000,
          pause: chartTap || chartFinish,
          onRefresh:
            chartTap === false || chartFinish === false ? onRecieve : null,
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
      <Line options={options} data={data} onClick={chartPauseTapping} />
    </div>
  );
}
