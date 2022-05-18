import React, { useEffect, useState } from "react";
import styles from "./StatisticSection.module.css";
import { ThreeDots } from "react-loader-spinner";

const MainSection: React.FC = () => {
  const [stat, setStat] = useState<any>([]);

  useEffect(() => {
    const fetchData = async () => {
      const response = await fetch(
        `${process.env.REACT_APP_LINK_SERVE}charge/statistic`
      );
      const json = await response.json();
      setStat(json);
    };
    fetchData();
  }, []);
  // console.log(stat.usage.monthly);
  return (
    <div className={styles.statisticBox}>
      {stat.usage ? (
        <div className={styles.power}>
          <h2 className={styles.monthlyPower}>
            Monthly: {stat.usage.monthly.toFixed(2)}W
          </h2>
          <div className={styles.tableList}>
            {stat.usage.daily.map((el: any) => (
              <div className={styles.listPower}>
                <p className={styles.powerInfo}>{el.day})</p>
                <p className={styles.powerInfo}>{el.usage}W</p>
              </div>
            ))}
          </div>
        </div>
      ) : (
        <div className={styles.load}>
          <ThreeDots color="#04AA6D" height={70} width={70} />
        </div>
      )}
    </div>
  );
};

export default MainSection;
