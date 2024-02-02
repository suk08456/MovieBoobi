package com.korea.MOVIEBOOK.payment;

import com.korea.MOVIEBOOK.book.Book;
import com.korea.MOVIEBOOK.book.BookRepository;
import com.korea.MOVIEBOOK.book.BookService;
import com.korea.MOVIEBOOK.drama.Drama;
import com.korea.MOVIEBOOK.drama.DramaRepository;
import com.korea.MOVIEBOOK.drama.DramaService;
import com.korea.MOVIEBOOK.member.Member;
import com.korea.MOVIEBOOK.member.MemberRepository;
import com.korea.MOVIEBOOK.movie.movie.Movie;
import com.korea.MOVIEBOOK.movie.movie.MovieRepository;
import com.korea.MOVIEBOOK.movie.movie.MovieService;
import com.korea.MOVIEBOOK.webtoon.webtoonList.Webtoon;
import com.korea.MOVIEBOOK.webtoon.webtoonList.WebtoonRepository;
import com.korea.MOVIEBOOK.webtoon.webtoonList.WebtoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static java.lang.Long.*;

@RequiredArgsConstructor
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;
    private final MovieRepository movieRepository;
    private final BookRepository bookRepository;
    private final DramaService dramaService;
    private final WebtoonRepository webtoonRepository;

    public void savePayment(Long id, String payment, String paidAmount, String paymentNo, String payType, String phone, String content, String contents, String contentsID, Pageable pageable) {
        Member member = this.memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Member not found"));
        Payment payment1 = new Payment();
        payment1.setPaymentCompany(payment);
        payment1.setPaidAmount(paidAmount);
        payment1.setPaymentNo(paymentNo);
        payment1.setPayType(payType);
        payment1.setPhone(phone);
        payment1.setMember(member);
        payment1.setDateTime(LocalDateTime.now());
        payment1.setContent(content);
        payment1.setContents(contents);
        payment1.setContentsID(contentsID);
        payment1.setDateTime(LocalDateTime.now());
        if (Objects.equals(contents, "movie")) {
            Movie movie = this.movieRepository.findBymovieCode(contentsID);
            payment1.setMovie(movie);
            this.paymentRepository.save(payment1); // Save payment information with associated movie
        } else if (Objects.equals(contents, "drama")) {
            Drama drama = this.dramaService.findByDramaId(Long.valueOf(contentsID));
            payment1.setDrama(drama);
        } else if (Objects.equals(contents, "book")) {
            Book book = this.bookRepository.findByIsbn(contentsID);
            payment1.setBook(book);
        } else if (Objects.equals(contents, "webtoon")) {
            Webtoon webtoon = this.webtoonRepository.findByWebtoonId(Long.valueOf(contentsID));
            payment1.setWebtoon(webtoon);
        }
//        Page<Movie> moviePurchases = movieService.moviePurchasePageForMember(id, pageable.getPageNumber());
        this.paymentRepository.save(payment1);
    }

    public List<Payment> findPaymentListByMember(Member member) {
        return this.paymentRepository.findBymember(member);
    }


    public Page<Payment> getPaidMovieList(Member member, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("dateTime"));
        Pageable pageable = PageRequest.of(page, 5, Sort.by(sorts));
        return paymentRepository.findByMemberAndContents(member, "movie", pageable);
    }

    public Page<Payment> getPaidDramaList(Member member, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("dateTime"));
        Pageable pageable = PageRequest.of(page, 5, Sort.by(sorts));
        return paymentRepository.findByMemberAndContents(member, "drama", pageable);
    }

    public Page<Payment> getPaidBookList(Member member, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("dateTime"));
        Pageable pageable = PageRequest.of(page, 5, Sort.by(sorts));
        return paymentRepository.findByMemberAndContents(member, "book", pageable);
    }

    public Page<Payment> getPaidWebtoonList(Member member, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("dateTime"));
        Pageable pageable = PageRequest.of(page, 5, Sort.by(sorts));
        return paymentRepository.findByMemberAndContents(member, "webtoon", pageable);
    }


    public Page<Payment> getPaymentsByMember(Member member, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("dateTime"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.paymentRepository.findByMember(member, pageable);
    }
}
