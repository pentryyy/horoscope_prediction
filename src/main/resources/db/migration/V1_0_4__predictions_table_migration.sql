-- Создание таблицы
CREATE TABLE IF NOT EXISTS predictions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    prediction_text VARCHAR(500) NOT NULL,
    gender ENUM('MALE', 'FEMALE') NOT NULL,
    zodiac ENUM('ARIES', 'TAURUS','GEMINI',
                'CANCER', 'LEO', 'VIRGO', 
                'LIBRA', 'SCORPIO', 'SAGITTARIUS', 
                'CAPRICORN', 'AQUARIUS', 'PISCES') NOT NULL,
    prediction_type ENUM('FUNNY', 'USEFUL', 'MIXED') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);