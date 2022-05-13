import React, { useEffect, useState } from 'react';
import styles from './MainSection.module.css';
import mainImg from '../../../assets/charging.png';
import { Link } from 'react-router-dom';
import {ThreeDots} from 'react-loader-spinner';
import ErrorPage from '../../../components/error-page/ErrorPage';



interface LinkPayment {
  message: string
}

const MainSection: React.FC = () => {
  const [link, setLink] = useState<LinkPayment>();
  const [err, setErr] = useState<string>('');

  useEffect(() => {
    try{
      const fetchData = async () => {
      const response = await fetch(`${process.env.REACT_APP_LINK_SERVE}`);
      setLink(await response.json());
    };
    fetchData();
    } catch(error: any) {
      if(error.response.status === 500) {
        setErr('error')
      }
    }
    
    
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
