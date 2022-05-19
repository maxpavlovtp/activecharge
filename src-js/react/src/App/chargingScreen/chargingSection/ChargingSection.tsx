import React, { useEffect, useState } from "react";
import styles from "./ChargingSection.module.css";
import Timer from "../../../components/timer/Timer";
import { ThreeDots } from "react-loader-spinner";

const MainSection: React.FC = () => {
  const [msg, setMsg] = useState<any>();

  useEffect(() => {
    const fetchData = async () => {
      const response = await fetch(
        `${process.env.REACT_APP_LINK_SERVE}charge/charging`
      );
      const json = await response.json();
      setMsg(json);
    };
    fetchData();
  }, []);
  console.log(msg);
  return (
    <div className={styles.chargingBox}>
      <div className={styles.contTimer}>
        {msg ? (
          <div>
            <Timer seconds={10} />
            <p>{msg.powerAgregation}</p>
          </div>
          
        ) : (
          <div className={styles.load}>
            <ThreeDots color="#04AA6D" height={70} width={70} />
          </div>
        )}
      </div>
    </div>
  );
};

export default MainSection;
