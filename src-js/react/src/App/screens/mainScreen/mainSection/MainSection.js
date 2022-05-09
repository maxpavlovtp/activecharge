import React, { useEffect, useState } from 'react';
import styles from './MainSection.module.css';
import mainImg from '../../../assets/charging.png';
import { Link } from 'react-router-dom';
import {ThreeDots} from 'react-loader-spinner';



interface LinkPayment {
  message: string
}

const MainSection: React.FC = () => {
  const [link, setLink] = useState<LinkPayment>();

  useEffect(() => {
    const fetchData = async () => {
      const response = await fetch(`http://localhost:5000`);
      setLink(await response.json());
    };
    fetchData();
    
  }, []);
  console.log(link);

  return (
    <div className={styles.mainBox}>
      {link ? (
        <div className={styles.container}>
          <h1 className={styles.title}>Заряди 220 кілометрів за ніч</h1>
          <div className={styles.btnStart}>
            <button
              className={styles.btnPay}
              onClick={() => window.open(link.message)}
            >
              Start
            </button>
            <Link className={styles.btn} to="/charging">
              Start Free
            </Link>
          </div>
          <div className={styles.imgCont}>
            <img className={styles.mainImg} src={mainImg} alt="mainImg" />
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
