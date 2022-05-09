import React, { useEffect, useState } from 'react';
import styles from './ChargingSection.module.css';
import Timer from '../../../components/timer/Timer';

const MainSection: React.FC = () => {
  const [msg, setMsg] = useState<string>();
  
  useEffect(() => {
    const fetchData = async () => {
      const response = await fetch(`http://localhost:5000/charge/charging`);
      const json = await response.json();
      setMsg(json);
      
    };
    fetchData();
  }, []);
  console.log(msg);
  return (
    <div className={styles.chargingBox}>
      <div>
        <Timer seconds={10} />
      </div>
    </div>
  );
};

export default MainSection;
