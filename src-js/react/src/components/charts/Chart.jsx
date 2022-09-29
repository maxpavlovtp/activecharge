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

export function Chart({ leftS, power, voltage, chartTap, setChartTap }) {
  const [powerChart, setPowerChart] = useState([]);
  const [voltageChart, setVoltageChart] = useState([]);

  const { t } = useTranslation();

  const labels = [];
  for (let time = 0; time <= 720; time++) {
    labels.push(time);
  }

  const chartTimerFinish = leftS === 0 ? true : false;

  const chartPauseTapping = () => {
    setChartTap(true);
  };

  const onRecieve = () => {
    console.log(powerChart);
    console.log(voltageChart);
    if (!chartTap) {
      setVoltageChart((old) => [
        ...old,
        {
          x: Date.now() + 12,
          y: voltage,
        },
      ]);
      setPowerChart((old) => [
        ...old,
        {
          x: Date.now(),
          y: power,
        },
      ]);
    }
  };

  const data = {
    datasets: [
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
      {
        label: t("power"),
        backgroundColor: "rgba(245, 188, 188, 0.5)",
        fill: true,
        lineTension: 0,
        borderDash: [8, 4],
        borderColor: "rgb(207, 149, 149)",
        cubicInterpolationMode: "monotone",
        yAxisID: "power",
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
      power: {
        type: "linear",
        display: true,
        position: "right",
        min: 0,
        max: 5,
      },
      voltage: {
        type: "linear",
        display: true,
        position: "left",
        min: 180,
        max: 260,

        // grid line settings
        grid: {
          drawOnChartArea: false, // only want the grid lines for one axis to show up
        },
      },

      x: {
        type: "realtime",
        distribution: "linear",
        realtime: {
          duration: 20000,
          // delay: 4000,
          refresh: 2050,
          pause: chartTap || chartTimerFinish,
          onRefresh: chartTap === false ? onRecieve : null,
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
    <div style={{ maxWidth: 700, width: "100%", padding: "0 20px 0 20px" }}>
      <Line
        options={options}
        data={data}
        onTouchStart={chartPauseTapping}
        onClick={chartPauseTapping}
      />
    </div>
  );
}
